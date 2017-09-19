package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RequestTry.
 */
@Entity
@Table(name = "request_try")
public class RequestTry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private Integer status;

    @NotNull
    @Column(name = "request_id", nullable = false)
    private Long requestId;

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

    public Long getRequestId() {
        return requestId;
    }

    public RequestTry requestId(Long requestId) {
        this.requestId = requestId;
        return this;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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
            ", requestId='" + getRequestId() + "'" +
            "}";
    }
}
