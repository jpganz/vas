package com.sigetel.web.web.rest.vm;

import com.sigetel.web.domain.Request;
import com.sigetel.web.domain.RequestParameter;
import com.sigetel.web.service.dto.UserDTO;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class OvasVM {

    /*public String clientName;
    public Long requestStatus;
    public String statusRequested;
    public String serviceCode;
    public String eventConfirmation;
    "clientName": "string",
"clientNumber": "string",
"annexedNumber": "string",
"requestedStatus": "string",
"serviceCode": "string",

    */
    public Map<String, String> parameters;
    public String serviceCode;
    public String requestedStatus;
    public String anneedNumber;
    public String clientNumber;


    public OvasVM() {
        // Empty constructor needed for Jackson.
    }
/*
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Long requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getStatusRequested() {
        return statusRequested;
    }

    public void setStatusRequested(String statusRequested) {
        this.statusRequested = statusRequested;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getEventConfirmation() {
        return eventConfirmation;
    }

    public void setEventConfirmation(String eventConfirmation) {
        this.eventConfirmation = eventConfirmation;
    }
*/
    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
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

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
