package com.sigetel.web.service.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigetel.web.domain.*; //fail
import com.sigetel.web.service.*;//mala practica
import com.sigetel.web.web.rest.consumer.RestClient;
import com.sigetel.web.web.rest.consumer.SoapClient;
import com.sun.jersey.api.client.ClientResponse;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.time.LocalDate;
import java.util.*;

import static com.sigetel.web.domain.RequestTryStatus.PENDING;
import static com.sigetel.web.domain.RequestTryStatus.TESTING;

@Service
public class EntrySaveService {


    @Autowired
    ProviderService providerService;

    @Autowired
    ProviderCommandService providerCommandService;

    @Autowired
    RequestParameterService requestParameterService;

    @Autowired
    RequestService requestService;

    @Autowired
    TryParameterService tryParameterService;

    @Autowired
    ProviderCommandRequestService providerCommandRequestService;

    @Autowired
    RequestTryService requestTryService;

    @Autowired
    SoapClient soapClient;

    @Autowired
    RestClient restClient;

    @Autowired
    TryResponseParameterService tryResponseParameterService;

    @Autowired
    ResponseParameterService responseParameterService;

    @Autowired
    ProviderResponseService providerResponseService;

    private final Logger log = LoggerFactory.getLogger(EntrySaveService.class);

    @Transactional
    public Long invokeAndSave(final RequestParams requestParams,
                              final Long providerCommandId,
                              final SaveRequestCommand saveRequestCommand,
                              final Map<String, String> stringParams,
                              final Map<String, Integer> intParams,
                              final boolean isRetry) throws JsonProcessingException {
        Map<String, String> headers = saveRequestCommand.getHeaders();
        Map<String, String> body = saveRequestCommand.getBody();
        Map<String, String> auth = saveRequestCommand.getAuth();
        Request flushedRequest = saveRequestCommand.getRequest();
        ProviderCommand providerCommand = providerCommandService.findOne(providerCommandId);
        Long tryId = 0l;
        try {
            if (providerCommandId == null) {
                return null;
            }

            RequestTry savedRequestTry = saveRequestTry(providerCommandId, flushedRequest, isRetry);
            tryId = savedRequestTry.getId();
            String authHeaders = "";
            List<ResponseObj> results = new ArrayList<>();
            if (providerCommand != null) {
                if (saveRequestCommand.getProtocol() == 1) { // REST
                    // convertir todos los headers
                    Map<String, String> hm = new HashMap<String, String>();
                    ServiceSecurity serviceSecurity = providerCommand.getServiceSecurity();
                    if (serviceSecurity.getId() == 0) {//NO AUTH
                        // DO NOTHING
                    } else if (serviceSecurity.getId() == 1) {//no auth (0) should never happen

                    } else if (serviceSecurity.getId() == 2) {//OAUTH2
                        String token = auth2Token(providerCommand.getSecurityParams());
                        //token = bearer 1234xvfzxfdsasad
                        if (token != null) {
                            hm.put("Authorization", token);
                        }
                    } else if (serviceSecurity.getId() == 3) {//BASIC
                        String token = basicAuthToken(providerCommand.getSecurityParams());
                        //token = bearer 1234xvfzxfdsasad
                        if (token != null) {
                            hm.put("Authorization", token);
                        }
                    }
                    //hm.put("content-type", "application/json");
                    Map<String, Object> mergedRequestParam = new HashMap<>();
                    mergedRequestParam.putAll(stringParams);
                    mergedRequestParam.putAll(intParams);
                    ObjectMapper objectMapper = new ObjectMapper(); // this is not threat safe, must keep it here
                    String mapToJson = objectMapper.writeValueAsString(mergedRequestParam);
                    // PARA EL OAUTH2 HACER UN HM.PUT CON EL AUTHORIZATION BEARER XXXXXX
                    ClientResponse response = restClient.postRequest(providerCommand.getEndpointUrl(), mapToJson, "application/json", hm);
                    List<LinkedHashMap> responseBody = restClient.parseResponse(response);
                    for (LinkedHashMap<String, String> innerMap : responseBody) {
                        for (Map.Entry<String, String> entry : innerMap.entrySet()) {
                            ResponseObj resp = new ResponseObj();
                            resp.setValue(entry.getValue());
                            resp.setName(entry.getKey()); // key
                            results.add(resp);
                        }
                    }

                } else if (saveRequestCommand.getProtocol() == 2) { // SOAP
                    Set<SecurityParams> securityParams = providerCommand.getSecurityParams();
                    if (securityParams.size() > 0) {
                        authHeaders += "<h:BasicAuth xmlns:h=\"http://soap-authentication.org/basic/2001/10/\"\n" +
                            "                   SOAP-ENV:mustUnderstand=\"1\">";
                        for (SecurityParams securityParam : securityParams) {
                            authHeaders += "<" + securityParam.getField() + ">";
                            authHeaders += securityParam.getValue();
                            authHeaders += "</" + securityParam.getField() + ">";
                        }
                        authHeaders += "</h:BasicAuth>";
                    }
                    Map<String, Map<String, String>> mapMap = new HashMap<>();
                    mapMap.put("body", body); // left here for nested resources... ask baires what the fuck
                    results = soapClient.requestSoap(
                        getValueOrEmpty("targetnamespace", headers),
                        getValueOrEmpty("servicename", headers),
                        getValueOrEmpty("portname", headers),
                        getValueOrEmpty("soapendpoint", headers),
                        getValueOrEmpty("soapaction", headers),
                        providerCommand.getEndpointUrl(),
                        mapMap,
                        authHeaders,
                        getValueOrEmpty("servicetargetnamespace", headers)
                    );

                }// END SOAP
            }
            List<String> resultParameters = new ArrayList<>(); // check if results are not empty :O
            for (ResponseObj result : results) {
                resultParameters.add(result.getName());
            }
            List<ProviderResponse> providerResponses = providerResponseService.findByAllProviderCommandId(providerCommandId);
            ProviderResponse selectedProviderResponse = null;
            for (ProviderResponse response : providerResponses) {
                List<String> savedResponsesParameters = new ArrayList<>();
                for (ResponseParameter paramter : response.getResponseParameters()) {
                    savedResponsesParameters.add(paramter.getName());
                }
                if ((savedResponsesParameters).containsAll(resultParameters)) {
                    selectedProviderResponse = response;
                    break;
                }
            }
            // HAD TO REMOVE FOREIGN KEY DUE IF DOESNT EXIST THE WHOLE TRANSACTION ROLLS BACK! X(
            RequestTry savedTry = requestTryService.findOne(tryId);
            //hago el match de parametros... viene el save :)
            for (final ResponseObj result : results) {
                TryResponseParameter tryResp = new TryResponseParameter();
                tryResp.setKey(result.getName());
                tryResp.setValue(result.getValue());
                if (savedTry != null) {
                    tryResp.setRequestTry(savedRequestTry);
                }
                //tryResp.setResponseParameter(responseParameterService.findByName(result.getName())); MUST STILL MATCH IT
                tryResponseParameterService.save(tryResp);
            }
            return tryId;
        } catch (Exception e) {
            // generic
            throw e;
        }
    }

    private RequestTry saveRequestTry(Long providerCommandId, Request flushedRequest, boolean isRetry) {
        if (!isRetry) {
            final ProviderCommandRequest providerCommandRequest = new ProviderCommandRequest();
            providerCommandRequest.setProviderCommandId(providerCommandId);
            providerCommandRequest.setRequestId(flushedRequest.getId());
            providerCommandRequestService.save(providerCommandRequest);
        }
        RequestTry requestTry = new RequestTry();
        requestTry.setRequest(flushedRequest);
        requestTry.setStatus(PENDING.getValue());
        requestTry.setDateTime(DateTime.now().toDate()); //CHECK THIS SHIT OUT
        requestTryService.save(requestTry);
        return requestTry;
    }

    private String basicAuthToken(Set<SecurityParams> securityParams) {
        Map<String, String> basicParams = new HashMap<>();
        for (SecurityParams securityParam : securityParams) {
            basicParams.put(securityParam.getField(), securityParam.getValue());
        }
        String authString = basicParams.get("user") + ":" + basicParams.get("password");
        String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
        return "Basic " + authStringEnc;

    }

    private String auth2Token(Set<SecurityParams> securityParams) throws JsonProcessingException {
        String urlPath = "";
        String mapToForm = "";
        boolean initial = true;
        for (SecurityParams securityParam : securityParams) {
            if (!initial) {
                mapToForm += "&";
            }
            if (securityParam.getSection().equals("body")) {
                mapToForm += securityParam.getField() + "=" + securityParam.getValue();
                initial = false;
            } else if (securityParam.getSection().equals("url")) {
                urlPath = securityParam.getValue();
            }

        }
        if (urlPath.length() < 1) {
            return null;
        }
        ClientResponse response = restClient.postRequest(urlPath, mapToForm, "application/x-www-form-urlencoded", new HashMap<String, String>());
        List<LinkedHashMap> responseBody = restClient.parseResponse(response);
        Map<String, String> resultOauth2 = new HashMap<>();
        for (LinkedHashMap<String, String> innerMap : responseBody) {
            for (Map.Entry<String, String> entry : innerMap.entrySet()) {
                resultOauth2.put(entry.getKey(), entry.getValue());
            }
        }
        String tokenType = resultOauth2.get("token_type");
        String token = resultOauth2.get("access_token");
        return tokenType + " " + token;
    }

    private String getValueOrEmpty(String key, Map<String, String> map) {
        if (map.get(key) == null) {
            return "";
        }
        return map.get(key);
    }
}
