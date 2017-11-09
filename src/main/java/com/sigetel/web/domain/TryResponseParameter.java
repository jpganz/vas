package com.sigetel.web.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TryResponseParameter.
 */
@Entity
@Table(name = "try_response_parameter")
public class TryResponseParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    @JsonIgnore
    private ResponseParameter responseParameter;

    @ManyToOne
    @JsonIgnore
    private RequestTry requestTry;

    @Column(name = "requestkey")
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public TryResponseParameter value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ResponseParameter getResponseParameter() {
        return responseParameter;
    }

    public TryResponseParameter responseParameter(ResponseParameter responseParameter) {
        this.responseParameter = responseParameter;
        return this;
    }

    public void setResponseParameter(ResponseParameter responseParameter) {
        this.responseParameter = responseParameter;
    }

    public RequestTry getRequestTry() {
        return requestTry;
    }

    public TryResponseParameter requestTry(RequestTry requestTry) {
        this.requestTry = requestTry;
        return this;
    }

    public void setRequestTry(RequestTry requestTry) {
        this.requestTry = requestTry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TryResponseParameter tryResponseParameter = (TryResponseParameter) o;
        if (tryResponseParameter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tryResponseParameter.getId());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TryResponseParameter{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
