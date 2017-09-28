package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_amount")
    private Long requestAmount;

    @Column(name = "client_number")
    private Long clientNumber;

    @Column(name = "annexed_number")
    private Long annexedNumber;

    @Column(name = "client_name")
    private String clientName;

    @NotNull
    @Column(name = "request_status", nullable = false)
    private Long requestStatus;

    @NotNull
    @Column(name = "status_requested", nullable = false)
    private String statusRequested;

    @NotNull
    @Column(name = "service_code", nullable = false)
    private String serviceCode;

    @Column(name = "event_confirmation")
    private String eventConfirmation;

    @Column(name = "next_try_by")
    private LocalDate nextTryBy;

    @Column(name = "date_time")
    private LocalDate dateTime;

    @OneToMany(mappedBy = "request", fetch = FetchType.LAZY)
    private List<RequestTry> requestTries = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestAmount() {
        return requestAmount;
    }

    public Request requestAmount(Long requestAmount) {
        this.requestAmount = requestAmount;
        return this;
    }

    public void setRequestAmount(Long requestAmount) {
        this.requestAmount = requestAmount;
    }

    public Long getClientNumber() {
        return clientNumber;
    }

    public Request clientNumber(Long clientNumber) {
        this.clientNumber = clientNumber;
        return this;
    }

    public void setClientNumber(Long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public Long getAnnexedNumber() {
        return annexedNumber;
    }

    public Request annexedNumber(Long annexedNumber) {
        this.annexedNumber = annexedNumber;
        return this;
    }

    public void setAnnexedNumber(Long annexedNumber) {
        this.annexedNumber = annexedNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public Request clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getRequestStatus() {
        return requestStatus;
    }

    public Request requestStatus(Long requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public void setRequestStatus(Long requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getStatusRequested() {
        return statusRequested;
    }

    public Request statusRequested(String statusRequested) {
        this.statusRequested = statusRequested;
        return this;
    }

    public void setStatusRequested(String statusRequested) {
        this.statusRequested = statusRequested;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public Request serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getEventConfirmation() {
        return eventConfirmation;
    }

    public Request eventConfirmation(String eventConfirmation) {
        this.eventConfirmation = eventConfirmation;
        return this;
    }

    public void setEventConfirmation(String eventConfirmation) {
        this.eventConfirmation = eventConfirmation;
    }

    public LocalDate getNextTryBy() {
        return nextTryBy;
    }

    public Request nextTryBy(LocalDate nextTryBy) {
        this.nextTryBy = nextTryBy;
        return this;
    }

    public void setNextTryBy(LocalDate nextTryBy) {
        this.nextTryBy = nextTryBy;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public Request dateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public List<RequestTry> getRequestTries() {
        return requestTries;
    }

    public Request requestTries(List<RequestTry> requestTries) {
        this.requestTries = requestTries;
        return this;
    }

    public Request addRequestTry(RequestTry requestTry) {
        this.requestTries.add(requestTry);
        requestTry.setRequest(this);
        return this;
    }

    public Request removeRequestTry(RequestTry requestTry) {
        this.requestTries.remove(requestTry);
        requestTry.setRequest(null);
        return this;
    }

    public void setRequestTries(List<RequestTry> requestTries) {
        this.requestTries = requestTries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Request request = (Request) o;
        if (request.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", requestAmount='" + getRequestAmount() + "'" +
            ", clientNumber='" + getClientNumber() + "'" +
            ", annexedNumber='" + getAnnexedNumber() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", requestStatus='" + getRequestStatus() + "'" +
            ", statusRequested='" + getStatusRequested() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", eventConfirmation='" + getEventConfirmation() + "'" +
            ", nextTryBy='" + getNextTryBy() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}
