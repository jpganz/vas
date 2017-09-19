package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @Column(name = "request_try_id", nullable = false)
    private Long requestTryId;

    @NotNull
    @Column(name = "response_parameter_id", nullable = false)
    private Long responseParameterId;

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

    public Long getRequestTryId() {
        return requestTryId;
    }

    public TryResponseParameter requestTryId(Long requestTryId) {
        this.requestTryId = requestTryId;
        return this;
    }

    public void setRequestTryId(Long requestTryId) {
        this.requestTryId = requestTryId;
    }

    public Long getResponseParameterId() {
        return responseParameterId;
    }

    public TryResponseParameter responseParameterId(Long responseParameterId) {
        this.responseParameterId = responseParameterId;
        return this;
    }

    public void setResponseParameterId(Long responseParameterId) {
        this.responseParameterId = responseParameterId;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TryResponseParameter{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", requestTryId='" + getRequestTryId() + "'" +
            ", responseParameterId='" + getResponseParameterId() + "'" +
            "}";
    }
}
