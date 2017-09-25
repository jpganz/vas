package com.sigetel.web.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TryParameter.
 */
@Entity
@Table(name = "try_parameter")
public class TryParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_value", nullable = false)
    private String value;

    @ManyToOne
    @JsonIgnore
    private Request request;

    @ManyToOne
    @JsonIgnore
    private RequestParameter requestParameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public TryParameter value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Request getRequest() {
        return request;
    }

    public TryParameter request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public RequestParameter getRequestParameter() {
        return requestParameter;
    }

    public TryParameter requestParameter(RequestParameter requestParameter) {
        this.requestParameter = requestParameter;
        return this;
    }

    public void setRequestParameter(RequestParameter requestParameter) {
        this.requestParameter = requestParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TryParameter tryParameter = (TryParameter) o;
        if (tryParameter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tryParameter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TryParameter{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
