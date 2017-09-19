package com.sigetel.web.soap.webServices;

import com.sigetel.web.soap.security.LoginRequest;
import com.sigetel.web.soap.security.LoginResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService( name = "Token")
@SOAPBinding(style= SOAPBinding.Style.RPC, use= SOAPBinding.Use.LITERAL)
public interface GenerateToken {

    @WebResult(name = "return", targetNamespace = "loginRequest")
    @RequestWrapper(targetNamespace = "http://service.ws.samp", className = "com.web.sigel.ws.soap.security.LoginRequest")
    @WebMethod(operationName = "getGenerateToken")
    @ResponseWrapper(targetNamespace = "http://service.ws.samp", className = "com.web.sigel.ws.soap.security.LoginResponse")
    public LoginResponse generateToken(@WebParam(name = "loginRequest", targetNamespace = "", mode = WebParam.Mode.IN) LoginRequest loginRequest);
}
