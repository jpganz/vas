package com.sigetel.web.config;

import com.sigetel.web.soap.SoapService;
import com.sigetel.web.soap.webServices.GenerateLoginToken;
import com.sigetel.web.web.rest.consumer.RestClient;
import com.sigetel.web.web.rest.consumer.SoapClient;
import com.sigetel.web.web.rest.util.JasperHelper;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.ws.Endpoint;
import java.lang.reflect.Field;

@Configuration
public class WebServiceConfig  extends HttpServlet {

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
    public RestClient restClient(){
        RestClient restClient = new RestClient();
        return restClient;
    }

    @Bean
    public SoapClient soapClient(){
        SoapClient soapClient = new SoapClient();
        return soapClient;
    }



    @Bean
    public Endpoint endpointToken() {

        EndpointImpl endpoint = new EndpointImpl(bus,generateLoginToken );


        endpoint.publish("/Token");

        return endpoint;
    }

    @Bean
    public JasperHelper jasperHelper() {
        JasperHelper helper = new JasperHelper("classpath:/billing.jrxml");
        return helper;
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
