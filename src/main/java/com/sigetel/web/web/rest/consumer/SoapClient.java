package com.sigetel.web.web.rest.consumer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import com.sigetel.web.domain.ResponseObj;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SoapClient {

     //List<ResponseObj> list = new ArrayList();
     public List<ResponseObj> requestSoap(String targetNamespace,
                               String serviceName,
                               String portName,
                               String SoapEndpoint,
                               String soapAction,
                               String endpointUrl,
                               Map<String, Map<String, String>> bodyParams,
                               String headers,
                               String serviceTargetNamespace) {

         /*
         <soapenv:Body>
             <urn:basicAuth soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                <customer xsi:type="xsd:string">?</customer>
                <act xsi:type="xsd:string">?</act>
                <text xsi:type="xsd:string">?</text>
                <username xsi:type="xsd:string">?</username>
                <password xsi:type="xsd:string">?</password>
             </urn:basicAuth>
          </soapenv:Body>
          */
        final String requestStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" "
            + "     xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "
            + "     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
            + "    <SOAP-ENV:Header>"
            +       headers
            + "    </SOAP-ENV:Header>"
            +      "<S:Body>";

        String requestBody = "";
        for (Map.Entry<String, Map<String, String>> entry : bodyParams.entrySet()) {
            requestBody += "<" + serviceName + " xmlns=\"" + serviceTargetNamespace + "\">";
            for (Map.Entry<String, String> value : entry.getValue().entrySet()) {
                requestBody += "<" + value.getKey() + ">";
                requestBody += value.getValue();
                requestBody += "</" + value.getKey() + ">";
            }
            requestBody += "</" + serviceName + ">";
        }
        final String requestEnd =
            "    </S:Body>"
                + "</S:Envelope>";

        QName qServiceName = new QName(targetNamespace, serviceName);
        QName qPortName = new QName(targetNamespace,    portName);
        SOAPBody response = invoke(qServiceName, qPortName, endpointUrl, soapAction, requestStart + requestBody + requestEnd);
        List<ResponseObj> objects = parseResponse(response);
        return objects;
    }

    public  SOAPBody invoke(QName serviceName, QName portName,
                                  String endpointUrl, String soapActionUri, String request) {
        Service service = Service.create(serviceName);
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);
        Dispatch dispatch = service.createDispatch(portName, SOAPMessage.class,
            Service.Mode.MESSAGE);
        SOAPMessage response;
        SOAPBody responseBody;
        Document document;
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, true);
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY,soapActionUri);
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            SOAPPart soapPart = message.getSOAPPart();
            StreamSource msgSrc = new StreamSource(new StringReader(request));
            soapPart.setContent(msgSrc);
            message.saveChanges();
            response = (SOAPMessage) dispatch.invoke(message);
            responseBody = response.getSOAPBody();
            if (responseBody.hasFault()) {
                throw new RuntimeException("Response fault : "
                    + responseBody.getFault());
            }

        } catch (Exception e) {
            throw new RuntimeException("HTTP error code : " + e);
        }
        return responseBody;
    }

    public  List<ResponseObj> parseResponse(SOAPBody docResponse) {
        List<ResponseObj> list = new ArrayList<>();
        //list = new ArrayList<>();
        try {
            NodeList nList = docResponse.getChildNodes();
            list = navigateChildNodes(nList, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private  List<ResponseObj> navigateChildNodes(NodeList nodeList, List<ResponseObj> list) {

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            if (node instanceof Element) {

                if (node.hasChildNodes()) {
                    ResponseObj obj = new ResponseObj();
                    obj.setName(node.getNodeName());
                    obj.setValue(node.getTextContent());
                    list.add(obj);
                    navigateChildNodes(node.getChildNodes(), list);
                } else if (!node.hasChildNodes()) {
                    ResponseObj obj = new ResponseObj();
                    obj.setName(node.getNodeName());
                    obj.setValue(node.getTextContent());
                    list.add(obj);
                }
            }
        }
        return list;
    }

}
