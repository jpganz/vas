package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "request_try")
public class RequestTry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private Integer status;

    @Column(name = "try_number")
    private Integer tryNumber;

    @Column(name = "date_time")
    private Date dateTime;



    @ManyToOne
    @JsonIgnore
    private Request request;

    @OneToMany(mappedBy = "requestTry", fetch = FetchType.LAZY)
    //@JsonIgnore
    private Set<TryResponseParameter> tryResponseParameters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public RequestTry status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Request getRequest() {
        return request;
    }

    public RequestTry request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Integer getTryNumber() {
        return tryNumber;
    }

    public void setTryNumber(Integer tryNumber) {
        this.tryNumber = tryNumber;
    }

    public Set<TryResponseParameter> getTryResponseParameters() {
        return tryResponseParameters;
    }

    public RequestTry tryResponseParameters(Set<TryResponseParameter> tryResponseParameters) {
        this.tryResponseParameters = tryResponseParameters;
        return this;
    }

    public RequestTry addTryResponseParameter(TryResponseParameter tryResponseParameter) {
        this.tryResponseParameters.add(tryResponseParameter);
        tryResponseParameter.setRequestTry(this);
        return this;
    }

    public RequestTry removeTryResponseParameter(TryResponseParameter tryResponseParameter) {
        this.tryResponseParameters.remove(tryResponseParameter);
        tryResponseParameter.setRequestTry(null);
        return this;
    }

    public void setTryResponseParameters(Set<TryResponseParameter> tryResponseParameters) {
        this.tryResponseParameters = tryResponseParameters;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestTry requestTry = (RequestTry) o;
        if (requestTry.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestTry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestTry{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
