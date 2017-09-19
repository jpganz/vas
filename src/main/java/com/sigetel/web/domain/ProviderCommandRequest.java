package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProviderCommandRequest.
 */
@Entity
@Table(name = "provider_command_request")
public class ProviderCommandRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "request_id", nullable = false)
    private Long requestId;

    @NotNull
    @Column(name = "provider_command_id", nullable = false)
    private Long providerCommandId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestId() {
        return requestId;
    }

    public ProviderCommandRequest requestId(Long requestId) {
        this.requestId = requestId;
        return this;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getProviderCommandId() {
        return providerCommandId;
    }

    public ProviderCommandRequest providerCommandId(Long providerCommandId) {
        this.providerCommandId = providerCommandId;
        return this;
    }

    public void setProviderCommandId(Long providerCommandId) {
        this.providerCommandId = providerCommandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderCommandRequest providerCommandRequest = (ProviderCommandRequest) o;
        if (providerCommandRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerCommandRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderCommandRequest{" +
            "id=" + getId() +
            ", requestId='" + getRequestId() + "'" +
            ", providerCommandId='" + getProviderCommandId() + "'" +
            "}";
    }
}
