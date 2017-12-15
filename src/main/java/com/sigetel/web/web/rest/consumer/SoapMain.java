package com.sigetel.web.web.rest.consumer;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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

public class SoapMain {

    static List<ResponseObj> list = new ArrayList();
    /* sample request.Here add the soap request. */
    static String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\" "
        + "     xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" "
        + "     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
        + "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"
        + "    <SOAP-ENV:Header/>" + "    <S:Body>"
        + "       <listAllFlights xmlns=\"http://soap.training.mulesoft.com/\">"
        + "   </listAllFlights>" + "    </S:Body>"
        + "</S:Envelope>";

    public static void main(String[] args) throws Exception {
        // put your soap service base url here
        String targetNameSpace = "http://soap.training.mulesoft.com/";

        // put soap method here. In your example it will be GetWhoIs
        QName serviceName = new QName(targetNameSpace, "DeltaFlightsService");

        // this is optional field. if you know port then put it otherwise send
        // blank string
        QName portName = new QName(targetNameSpace, "DeltaFlightsServiceImplPort");
        // put your service url here. The example link which you shared will
        // come here.
        String endpointUrl = "http://mu.mulesoft-training.com/essentials/delta";
        // you find the action in your soap request header information.
        // generally it will contain the soap url with the specific soap method
        // which you are accessing
        String SOAPAction = "";

        // response object is returned here.
        SOAPBody responseDocument = invoke(serviceName, portName, endpointUrl,
            SOAPAction, request);
        // System.out.println(map);
        List<ResponseObj> list = parseResponse(responseDocument);
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getName() + "  "
                + list.get(i).getValue());
            System.out.println("+++++++++++++++++++++");
        }


    }

    // this method is used to invoke soap service. to use this method pass all
    // the required parameter as I have done in the main method. This method
    // will return the response object.
    public static SOAPBody invoke(QName serviceName, QName portName,
                                  String endpointUrl, String soapActionUri, String request) {
        // this will create a service object of the method name which you shared
        Service service = Service.create(serviceName);
        // add portif present
        service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);
        // Creates a Dispatch instance for use with objects of the client's
        // choosing
		/*
		 * Parameters: portName - Qualified name for the target service endpoint
		 * type - The class of object used for messages or message payloads.
		 * Implementations are required to support javax.xml.transform.Source,
		 * javax.xml.soap.SOAPMessage and javax.activation.DataSource, depending
		 * on the binding in use. mode - Controls whether the created dispatch
		 * instance is message or payload oriented, i.e. whether the client will
		 * work with complete protocol messages or message payloads. E.g. when
		 * using the SOAP protocol, this parameter controls whether the client
		 * will work with SOAP messages or the contents of a SOAP body. Mode
		 * MUST be MESSAGE when type is SOAPMessage.
		 */
        Dispatch dispatch = service.createDispatch(portName, SOAPMessage.class,
            Service.Mode.MESSAGE);
        SOAPMessage response;
        SOAPBody responseBody;
        Document document;
		/*
		 * This is used to get the context that is used to initialize the
		 * message context for request messages. Here the required key/value
		 * pair are put in context map
		 */
        dispatch.getRequestContext()
            .put(Dispatch.SOAPACTION_USE_PROPERTY, true);
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY,
            soapActionUri);
        try {
            // new instance of message factory created
            MessageFactory messageFactory = MessageFactory.newInstance();
            // Creates a new SOAPMessage object with the default SOAPPart,
            // SOAPEnvelope, SOAPBody, and SOAPHeader objects
            SOAPMessage message = messageFactory.createMessage();
            SOAPPart soapPart = message.getSOAPPart();
            StreamSource msgSrc = new StreamSource(new StringReader(request));
            soapPart.setContent(msgSrc);
            // Updates this SOAPMessage object with all the changes that have
            // been made to it
            message.saveChanges();
            // invoke method of the dispatch is used to get response
            response = (SOAPMessage) dispatch.invoke(message);
            // fetch the soap body from the response
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

    // method is used to parse XML response and access any xml item using
    // document parameter
    public static List<ResponseObj> parseResponse(SOAPBody docResponse) {
        List<ResponseObj> list = null;
        try {
            // Get all child elements
            NodeList nList = docResponse.getChildNodes();

            list = navigateChildNodes(nList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<ResponseObj> navigateChildNodes(NodeList nodeList) {

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            Node node = nodeList.item(temp);
            if (node instanceof Element) {

                if (node.hasChildNodes()) {
                    ResponseObj obj = new ResponseObj();
                    obj.setName(node.getNodeName());
                    obj.setValue(node.getTextContent());
                    list.add(obj);
                    navigateChildNodes(node.getChildNodes());
                } else if (!node.hasChildNodes()) {
                    ResponseObj obj = new ResponseObj();
                    obj.setName(node.getNodeName());
                    obj.setValue(node.getTextContent());
                    list.add(obj);
                    // map.put(node.getNodeName(), node.getTextContent());
                }
            }
        }
        return list;
    }

}
