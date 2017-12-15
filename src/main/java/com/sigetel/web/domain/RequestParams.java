package com.sigetel.web.domain;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "requestParams", namespace="http://service.ws.samp")
public class RequestParams {
    @XmlElement(name = "token", required = true)
    private String token;

    @XmlElement(name = "serviceCode", required = false)
    private String serviceCode;//command number
    @XmlElement(name = "requestedStatus", required = false)
    private String requestedStatus;//

    @XmlElement(name = "anneedNumber", required = false)
    private String anneedNumber;

    @XmlElement(name = "clientNumber", required = false)
    private String clientNumber;

    @XmlElement(name = "phoneNumber", required = false)
    private String phoneNumber;

    @XmlElement(name = "optionMap", required = false)
   private Map<String,String> optionMap;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getRequestedStatus() {
        return requestedStatus;
    }

    public void setRequestedStatus(String requestedStatus) {
        this.requestedStatus = requestedStatus;
    }

    public String getAnneedNumber() {
        return anneedNumber;
    }

    public void setAnneedNumber(String anneedNumber) {
        this.anneedNumber = anneedNumber;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public Map<String, String> getOptionMap() {
        return optionMap;
    }

    public void setOptionMap(Map<String, String> optionMap) {
        this.optionMap = optionMap;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RequestParams{" +
            "serviceCode='" + serviceCode + '\'' +
            "token='" + token + '\'' +
            ", requestedStatus='" + requestedStatus + '\'' +
            ", anneedNumber='" + anneedNumber + '\'' +
            ",clientNumber='" + clientNumber + '\'' +
            ",phoneNumber='" + phoneNumber + '\'' +
            ", optionMap='" + optionMap + '\'' +
            '}';
    }
}
