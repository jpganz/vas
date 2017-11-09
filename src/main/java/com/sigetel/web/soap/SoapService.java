package com.sigetel.web.soap;

import com.sigetel.web.domain.RequestParams;
import com.sigetel.web.domain.ResponseObj;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@WebService( name = "SoapService")
@SOAPBinding(style= SOAPBinding.Style.RPC, use= SOAPBinding.Use.LITERAL)
public interface SoapService {
    @WebResult(name = "return", targetNamespace = "requestParams")
    @RequestWrapper(targetNamespace = "http://service.ws.samp", className = "com.sigetel.web.soap.RequestParams")
    @WebMethod(operationName = "requestSoap")
    @ResponseWrapper(targetNamespace = "http://service.ws.samp", className = "com.sigetel.web.soap.ResponseObject")
    ReponseObject requestSoap(@WebParam(name = "requestParams", targetNamespace = "", mode = WebParam.Mode.IN) RequestParams requestParams);

    boolean invokeLdap(String username, String password);

    void invokeRetryRequest();
}
