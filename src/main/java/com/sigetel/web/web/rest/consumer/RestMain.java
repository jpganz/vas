package com.sigetel.web.web.rest.consumer;

import com.sigetel.web.domain.ResponseObj;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.*;

/**
 * Created by user on 28/10/2017.
 */
public class RestMain {

    public static final String FEATURE_POJO_MAPPING = "com.sun.jersey.api.json.POJOMappingFeature";

    public static void main(String[] args) {
        Map<String, String> hm = new HashMap<String, String>();
        hm.put("content-type", "text/xml");
        String postBody = "{ \"code\": \"GQ574\", \"price\": 399, \"departureDate\": \"2016/12/20\",\"origin\": \"ORD\", \"destination\": \"SFO\", \"emptySeats\": 200, \"plane\": {\"type\": \"Boeing 747\", \"totalSeats\": 400}}";

        RestMain restClient = new RestMain();
        ClientResponse response = restClient.postRequest(
            "http://apdev-american-ws.cloudhub.io/api/flights", postBody,
            "application/json", hm);
        List<LinkedHashMap> responseBody = restClient.parseResponse(response);
        LinkedHashMap<String, String> myMap = responseBody.get(0);
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            ResponseObj resp = new ResponseObj();
            resp.setValue(entry.getValue());
            resp.setName(entry.getKey()); // key
            System.out.println(entry.getValue());
            System.out.println(entry.getKey());
        }

        //System.out.println(responseBody.get(0).keySet());
        //System.out.println(responseBody.get(0).get("message"));

        Map<String, String> pl = new HashMap<String, String>();

        /*pl.put("CountryName", "india");
        // pl.put("origin", "MUA");

        ClientResponse responsee = restClient.getRequest(
            "http://apdev-american-ws.cloudhub.io/api/flights", pl,
            "application/json", hm);
        List<LinkedHashMap> responseBody2 = restClient.parseResponse(responsee);
        System.out.println(responseBody2.get(0).keySet());
        System.out.println(responseBody2.get(0).get("code"));
        System.out.println(responseBody2.get(0).get("ID"));
        System.out.println(responseBody2.get(0).get("origin"));*/
    }

    /* get request */
    public ClientResponse getRequest(String url, Map<String, String> paramList,
                                     String contentType, Map<String, String> headerMap) {
        ClientConfig config = new DefaultClientConfig();
        config.getFeatures().put(FEATURE_POJO_MAPPING,
            Boolean.TRUE);
        Client client = Client.create(config);
        WebResource resource = client.resource(url);
        ClientResponse response = null;

        if (paramList.size() > 0) {
            for (String param : paramList.keySet()) {
                resource = resource.queryParam(param, paramList.get(param));
            }
        }

        if (headerMap.size() > 0) {
            WebResource.Builder resourceBuilder = null;
            for (String head : headerMap.keySet()) {
                resourceBuilder = resource.header(head, headerMap.get(head));
            }
            response = resourceBuilder.type(contentType).get(
                ClientResponse.class);
        } else {
            response = resource.get(ClientResponse.class);
        }
        if (response.getStatus() != 200) {
            throw new RuntimeException("HTTP error code : "
                + response.getStatus());
        }

        return response;
    }

    /* post request */
    public ClientResponse postRequest(String url, String param,
                                      String contentType, Map<String, String> headerMap) {
        ClientResponse response = null;
        try {

            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(
                FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);

            WebResource webResource = client.resource(url);
            if (headerMap.size() > 0) {
                WebResource.Builder resourceBuilder = null;
                for (String head : headerMap.keySet()) {
                    resourceBuilder = webResource.accept(contentType).header(
                        head, headerMap.get(head));
                }
                response = resourceBuilder.type(contentType).post(
                    ClientResponse.class, param);
            } else {
                response = webResource.type(contentType).post(
                    ClientResponse.class, param);
            }
            if (response.getStatus() != 201) {
                throw new RuntimeException("HTTP error code : "
                    + response.getStatus());
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return response;
    }

    /* parse response object to get list of key/value */
    public <T> List<T> parseResponse(ClientResponse response) {
        List<T> result = new ArrayList<T>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
            DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(
            DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
            true);
        String output = response.getEntity(String.class);
        try {
            result = mapper.readValue(output, new TypeReference<List<T>>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
