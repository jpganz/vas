package com.sigetel.web.domain;


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

    @NotNull
    @Column(name = "request_id", nullable = false)
    private Long requestId;

    @NotNull
    @Column(name = "request_parameter_id", nullable = false)
    private Long requestParameterId;

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

    public Long getRequestId() {
        return requestId;
    }

    public TryParameter requestId(Long requestId) {
        this.requestId = requestId;
        return this;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getRequestParameterId() {
        return requestParameterId;
    }

    public TryParameter requestParameterId(Long requestParameterId) {
        this.requestParameterId = requestParameterId;
        return this;
    }

    public void setRequestParameterId(Long requestParameterId) {
        this.requestParameterId = requestParameterId;
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
            ", requestId='" + getRequestId() + "'" +
            ", requestParameterId='" + getRequestParameterId() + "'" +
            "}";
    }
}
