package com.sigetel.web.soap.webServices;

import com.sigetel.web.soap.security.AuthService;
import com.sigetel.web.soap.security.LoginRequest;
import com.sigetel.web.soap.security.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;


@WebService(serviceName = "GenerateToken", portName = "TokenPort",
    targetNamespace = "http://service.ws.samp",
    endpointInterface = "com.sigetel.web.soap.webServices.GenerateToken")
@Service
public class GenerateLoginToken implements  GenerateToken {

    @Autowired
    private  AuthService authService;




    @Override
    @RequestWrapper(localName = "loginRequest", targetNamespace = "http://service.ws.samp", className = "com.sigetel.web.soap.security.LoginRequest")
    public LoginResponse generateToken(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
         String token  = authService.createAuthToken(loginRequest);

         loginResponse.setToken(token);

        return loginResponse;
    }
}

