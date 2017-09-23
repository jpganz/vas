package com.sigetel.web.soap;

import com.sigetel.web.soap.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.RequestWrapper;
import java.util.HashMap;
import java.util.Map;

@Service
@javax.jws.WebService(serviceName = "SoapService", portName = "SoapRequestPort",
    targetNamespace = "http://service.ws.sample/",
    endpointInterface = "com.sigetel.web.soap.SoapService")
public class SoapServiceImpl implements SoapService {
    @Autowired
    private AuthService authService;

    @RequestWrapper(localName = "requestParams", targetNamespace = "http://service.ws.sample/", className = " com.sigetel.web.soap.RequestParams")

    public ReponseObject requestSoap(RequestParams requestParams) {

        ReponseObject reponseObject = new ReponseObject();
        try {
            Boolean tokenVerified = authService.checkLogin(requestParams.getToken());

            reponseObject.setTokenVerified(tokenVerified);
           if(!tokenVerified){
             throw new Exception("Token is not available or expired");
           }

            Map newMmap = new HashMap<String, String>();
            newMmap.put("ResponseCode", "200");
            reponseObject.setMapObject(newMmap);

            return reponseObject;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
