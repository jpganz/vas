package com.sigetel.web.soap;

import com.sigetel.web.domain.*;
import com.sigetel.web.mail.MailBody;
import com.sigetel.web.mail.MailMail;
import com.sigetel.web.service.*;
import com.sigetel.web.service.validator.EntrySaveService;
import com.sigetel.web.service.validator.EntryValidator;
import com.sigetel.web.soap.security.AuthService;
import com.sigetel.web.web.rest.consumer.SoapClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.ws.RequestWrapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sigetel.web.domain.RequestTryStatus.PENDING;
import static com.sigetel.web.domain.RequestTryStatus.REQUESTED;
import static com.sigetel.web.domain.RequestTryStatus.TRY_AGAIN;

@Service
@javax.jws.WebService(serviceName = "SoapService", portName = "SoapRequestPort",
    targetNamespace = "http://service.ws.sample/",
    endpointInterface = "com.sigetel.web.soap.SoapService")
public class SoapServiceImpl implements SoapService {
    @Autowired
    private AuthService authService;

    @Autowired
    EntryValidator entryValidator;

    @Autowired
    EntrySaveService entrySaveService;

    @Autowired
    ProviderCommandService providerCommandService;

    @Autowired
    RequestParameterService requestParameterService;

    @Autowired
    RequestService requestService;

    @Autowired
    TryParameterService tryParameterService;

    @Autowired
    AppConfigService appConfigService;

    @Autowired
    SoapClient soapClient;

    @Autowired
    ProviderCommandRequestService providerCommandRequestService;

    private final Logger log = LoggerFactory.getLogger(SoapService.class);

    @RequestWrapper(localName = "requestParams", targetNamespace = "http://service.ws.sample/", className = " com.sigetel.web.soap.RequestParams")

    @Override
    public ReponseObject requestSoap(RequestParams requestParams) {
        Long savedInvoke = 0L;
        ReponseObject reponseObject = new ReponseObject();
        Map newMmap = new HashMap<String, String>();
        Long validEntry = 0L;
        try {
            Boolean tokenVerified = authService.checkLogin(requestParams.getToken());

            reponseObject.setTokenVerified(tokenVerified);
            if (!tokenVerified) {
                throw new Exception("Token is not available or expired");
            }

            Map stringParams = new HashMap<String, String>();
            Map intParams = new HashMap<String, Integer>();
            validEntry = entryValidator.isValidEntry(requestParams);
            Long savedValue = 0l;
            if (validEntry != null && validEntry > 0) {
                //new add...
                //tiene que ir a traer los no mandatorios y ver si tienen default value...
                List<RequestParameter> additionalParams = requestParameterService.findByAllProviderCommandId(validEntry);
                // come to get all the params each by each n get them string or int
                for (RequestParameter param : additionalParams) {
                    if (!param.isIsMandatory() && param.getDefaultValue().length() > 0) {
                        requestParams.getOptionMap().put(param.getName(), param.getDefaultValue());
                    }
                }
                // double loop needed
                for (RequestParameter param : additionalParams) {
                    // this is jut for rest
                    //Map<String,String>
                    for (Map.Entry<String, String> entry : requestParams.getOptionMap().entrySet()) {
                        if (param.getName().equals(entry.getKey())) {
                            if (param.getType().equals("string")) { // STRING
                                stringParams.put(entry.getKey(), entry.getValue());
                            } else if (param.getType().equals("integer")) { // INTEGER
                                intParams.put(entry.getKey(), Integer.valueOf(entry.getValue()));
                            }
                        }
                    }
                }
                //saving request
                SaveRequestCommand savedRequest = saveRequest(requestParams, validEntry);
                if (savedRequest != null) {
                    savedValue = savedRequest.getRequest().getId();
                    // consume soap endpoint
                    //check if is soap or rest
                    savedInvoke = entrySaveService.invokeAndSave(requestParams, validEntry, savedRequest, stringParams, intParams);
                    if (savedInvoke > 0) {
                        Request freshRequest = requestService.findOne(savedValue);
                        freshRequest.setRequestStatus(Long.valueOf(REQUESTED.getValue()));
                        requestService.save(freshRequest);
                    } else {
                        Request freshRequest = requestService.findOne(savedValue);
                        freshRequest.setRequestStatus(Long.valueOf(TRY_AGAIN.getValue()));
                        requestService.save(freshRequest);
                    }
                }
            }

            if (savedValue != 0) {
                newMmap.put("ResponseCode", "200");
                if (savedInvoke > 0) {
                    newMmap.put("ResponseMessage", "We saved and processed your request.");
                } else {
                    newMmap.put("ResponseMessage", "We saved your request but at the moment could not process it.");
                }
            } else if (validEntry == null) {
                newMmap.put("ResponseCode", "500");
                newMmap.put("ResponseMessage", "We could not process your request.");
            } else if (validEntry == 0) {
                newMmap.put("ResponseCode", "404");
                newMmap.put("ResponseMessage", "We could not match any information with the given request.");
            }
            reponseObject.setMapObject(newMmap);

            final ProviderCommand command = providerCommandService.findOne(validEntry);
            final String emailMessage = createMailMessage(command, false);
            notifyByEmail("sigel@mail.com", command.getEmailAddressToNotify(), emailMessage, "Request Fail Notification");
            return reponseObject;
        } catch (Exception ex) {
            ex.printStackTrace();
            newMmap.put("ResponseCode", "500");
            newMmap.put("ResponseMessage", "We could not process your request.");
            reponseObject.setMapObject(newMmap);

            // **************************** SEND EMAIL *************************
            if (validEntry > 0) {
                ProviderCommand command = providerCommandService.findOne(validEntry);
                String emailMessage = createMailMessage(command, true);
                notifyByEmail("sigel@mail.com", command.getEmailAddressToNotify(), emailMessage, "Request Fail Notification");
            }
            return reponseObject;
            //throw new RuntimeException(ex);
        }
    }

    @Transactional
    private SaveRequestCommand saveRequest(RequestParams requestParams, Long providerCommandId) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        Map<String, String> auth = new HashMap<>();
        ProviderCommand providerCommand = null;
        try {
            if (providerCommandId != null) {
                providerCommand = providerCommandService.findOne(providerCommandId);

                Request request = new Request();
                request.setAnnexedNumber(Long.valueOf(requestParams.getAnneedNumber()));
                request.setStatusRequested(requestParams.getRequestedStatus());
                request.setServiceCode(requestParams.getServiceCode());
                request.setClientNumber(Long.valueOf(requestParams.getClientNumber()));
                request.setClientName(providerCommand.getProvider().getName());
                request.setRequestStatus(Long.valueOf(PENDING.getValue()));
                request.setClientPhone(requestParams.getPhoneNumber());

                Map<String, String> options = requestParams.getOptionMap();
                String eventConfirmation = options.get("eventconfirmation");
                String requestAmount = options.get("requestamount");
                String statusRequested = options.get("statusRequested");
                if (eventConfirmation != null) {
                    request.setEventConfirmation(eventConfirmation);
                }

                if (requestAmount != null) {
                    request.setRequestAmount(Long.valueOf(requestAmount));
                }

                if (statusRequested != null) {
                    request.setStatusRequested(statusRequested);
                }

                List<TryParameter> tryParameters = new ArrayList<>();
                for (Map.Entry<String, String> entry : options.entrySet()) {
                    RequestParameter requestParameter = requestParameterService.findByName(entry.getKey());
                    if (requestParameter != null) {
                        TryParameter tryParameter = new TryParameter();
                        tryParameter.setRequestParameter(requestParameter);
                        tryParameter.setValue(entry.getValue());
                        tryParameters.add(tryParameter);
                        if (requestParameter.getSection().equals("body")) {
                            body.put(entry.getKey(), entry.getValue());
                        } else if (requestParameter.getSection().equals("header")) {
                            //headers.put(TAG, VALUE);
                            headers.put(entry.getKey(), entry.getValue());
                        }
                    } else {
                        log.debug("No request param found for entry key : {}", entry.getKey());
                    }
                }

                request.dateTime(LocalDate.now());
                requestService.save(request);
                Request flushedRequest = requestService.findOne(request.getId());
                for (TryParameter tryParameter : tryParameters) {
                    tryParameter.setRequest(flushedRequest);
                    tryParameterService.save(tryParameter);
                }
                SaveRequestCommand savedRequestCommand = new SaveRequestCommand();
                savedRequestCommand.setRequest(flushedRequest);
                savedRequestCommand.setAuth(auth);
                savedRequestCommand.setBody(body);
                savedRequestCommand.setHeaders(headers);
                savedRequestCommand.setProtocol(providerCommand.getCommunicationStandard().getId());
                return savedRequestCommand;
            }
        } catch (ClassCastException e) {
            //exception por cast
        } catch (Exception e) {
            // generic
        }
        return null;
    }

    @Override
    public boolean invokeLdap(String username, String password) {
        boolean isSuccess = false;
        try {
            String ldapId = appConfigService.findOneByRoute("ldap.ldapID").getValue();
            String userNameWS = appConfigService.findOneByRoute("ldap.username").getValue();
            String passwordWS = appConfigService.findOneByRoute("ldap.password").getValue();

            final String postData =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                    + "<soapenv:Envelope xmlns:aut=\"http://authservices.ws.gt.tigo.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "   <soapenv:Header>\n" +
                    "      <wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">\n" +
                    "         <wsse:UsernameToken wsu:Id=\"UsernameToken-2FAB27AACF0294DBA814975587082832\">\n" +
                    "            <wsse:Username>" + userNameWS + "</wsse:Username>\n" +
                    "            <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">" + passwordWS + "</wsse:Password>\n" +
                    "            <wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">pKYG6DiZ+/DXnDa9jkWMVg==</wsse:Nonce>\n" +
                    "            <wsu:Created>2017-06-15T20:31:48.282Z</wsu:Created>\n" +
                    "         </wsse:UsernameToken>\n" +
                    "      </wsse:Security>\n" +
                    "   </soapenv:Header>\n" +
                    "   <soapenv:Body>\n" +
                    "      <aut:requestUserInfo>\n" +
                    "         <ldapId>" + ldapId + "</ldapId>\n" +
                    "         <user>" + username + "</user>\n" +
                    "         <password>" + password + "</password>\n" +
                    "      </aut:requestUserInfo>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>";

            QName qServiceName =
                new QName("http://authservices.ws.gt.tigo.com/", "AuthenticationServiceSoapBindingQSService");
            QName qPortName =
                new QName("http://authservices.ws.gt.tigo.com/", "");
            SOAPBody response =
                soapClient.invoke(qServiceName, qPortName,
                    "http://172.30.13.44:8011/DTHWeb/AuthenticationService/V1",
                    "requestUserInfo", postData);

            List<ResponseObj> listResponse = soapClient.parseResponse(response);

            for (ResponseObj responseObj : listResponse) {
                if (responseObj.getName().equals("errorMessage")) {
                    if (responseObj.getValue().equals("success")) {
                        isSuccess = true;
                    }
                }
            }

            return isSuccess;
        } catch (Exception e) {
            log.error(e.getMessage());
            return isSuccess;
        }
    }


    @Override
    @Transactional
    public void invokeRetryRequest() {
        List<Request> missingRequests = requestService.findAllByRequestStatus(Long.valueOf(PENDING.getValue()));
        for (Request request : missingRequests) {
            ProviderCommandRequest providerCommandRequest = providerCommandRequestService.findByRequestId(request.getId());

        }

    }

    private String createMailMessage(ProviderCommand command, boolean failed) {
        String typeMessage = "";
        if (failed) {
            typeMessage += "Lamentamos notificar que la transaccion con los siguientes datos ha fallado, seguiremos intentando  " + (command.getRetryLimit() - 1) + " mas por una respuesta exitosa.";
        } else {
            typeMessage += "Notificamos que su transaccion ha sido exitosa con los siguientes datos";
        }
        log.info("-----------------------------SENDING EMAIL OF ERROR!!-----------------------------");
        String message = "";
        message += typeMessage;
        message += "< /br>";
        message += "Retry limit: " + command.getRetryLimit() + "< /br>";
        message += "Protocol: " + command.getCommunicationStandard().getName() + "< /br>";
        message += "Command: " + command.getCommand().getName() + "< /br>";
        message += "Code: " + command.getCode() + "< /br>";
        message += "Producto: " + command.getProducto() + "< /br>";
        message += "< /br>";
        message += "< /br>";
        // since this method is not transactional must do this
        List<RequestParameter> parameters = requestParameterService.findByAllProviderCommandId(command.getId());
        for (RequestParameter param : parameters) {
            message += param.getName() + " on  section " + param.getSection();
            message += "< /br>";
        }
        return message;
    }

    @Async
    private void notifyByEmail(String from, String to, String message, String subject) {
        try {
            MailMail mail = new MailMail();
            mail.setMailSender(mail.getDefaultMailConfiguration(appConfigService));
            MailBody mailBody = new MailBody();
            mailBody.setFrom(from);
            mailBody.setMessage(message);
            mailBody.setSubject(subject);
            mailBody.setTo(to);
            mail.constructMail(mailBody);
        } catch (Exception e) {
            log.info("Could not send notification email");
        }
    }

}
