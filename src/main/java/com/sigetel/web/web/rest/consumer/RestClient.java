package com.sigetel.web.web.rest.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class RestClient extends SpringServlet {

    private static final long serialVersionUID = 1L;
    public static final String FEATURE_POJO_MAPPING = "com.sun.jersey.api.json.POJOMappingFeature";


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

    public <T> List<T> parseResponse(ClientResponse response) {
        List<T> result = new ArrayList<T>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(
            DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(
            DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
            true);
        try {
            String output = response.getEntity(String.class);
            result = mapper.readValue(output, new TypeReference<List<T>>() {
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

