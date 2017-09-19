package com.sigetel.web.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService( name = "SoapService")
@SOAPBinding(style= SOAPBinding.Style.RPC, use= SOAPBinding.Use.LITERAL)
public interface SoapService {
    @WebResult(name = "return", targetNamespace = "requestParams")
    @RequestWrapper(targetNamespace = "http://service.ws.samp", className = "com.web.sigel.ws.soap.RequestParams")
    @WebMethod(operationName = "requestSoap")
    @ResponseWrapper(targetNamespace = "http://service.ws.samp", className = "com.web.sigel.ws.soap.ResponseObject")
    public ReponseObject requestSoap(@WebParam(name = "requestParams", targetNamespace = "", mode = WebParam.Mode.IN) RequestParams requestParams);




}
