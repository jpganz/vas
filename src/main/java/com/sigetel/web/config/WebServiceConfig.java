package com.sigetel.web.config;

import com.sigetel.web.soap.SoapService;
import com.sigetel.web.soap.webServices.GenerateLoginToken;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.xml.ws.Endpoint;
import java.lang.reflect.Field;


@Configuration
public class WebServiceConfig {


    @Autowired
    private Bus bus;

    @Autowired
    private GenerateLoginToken generateLoginToken;
    @Autowired
    private SoapService soapService;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, soapService);

        endpoint.publish("/SoapService");

        return endpoint;
    }
    @Bean
    public Endpoint endpointToken() {

        EndpointImpl endpoint = new EndpointImpl(bus,generateLoginToken );


        endpoint.publish("/Token");

        return endpoint;
    }


    private XmlWebApplicationContext getContext() {
        try {
            Field field = CXFServlet.class.getDeclaredField("createdContext");
            field.setAccessible(true);
            return (XmlWebApplicationContext) field.get(this);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Unable to autowire endpoint impementors registered by CXFServlet.", e);
        }
    }



}
