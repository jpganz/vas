package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProviderCommand.
 */
@Entity
@Table(name = "provider_command")
public class ProviderCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "endpoint_url", nullable = false)
    private String endpointUrl;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "retry_limit", nullable = false)
    private Long retryLimit;

    @NotNull
    @Column(name = "retry_interval", nullable = false)
    private Long retryInterval;

    @NotNull
    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @NotNull
    @Column(name = "command_id", nullable = false)
    private Long commandId;

    @NotNull
    @Column(name = "communication_standard_id", nullable = false)
    private Long communicationStandardId;

    @NotNull
    @Column(name = "service_security_id", nullable = false)
    private Long serviceSecurityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public ProviderCommand endpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public String getCode() {
        return code;
    }

    public ProviderCommand code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getRetryLimit() {
        return retryLimit;
    }

    public ProviderCommand retryLimit(Long retryLimit) {
        this.retryLimit = retryLimit;
        return this;
    }

    public void setRetryLimit(Long retryLimit) {
        this.retryLimit = retryLimit;
    }

    public Long getRetryInterval() {
        return retryInterval;
    }

    public ProviderCommand retryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public void setRetryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Long getProviderId() {
        return providerId;
    }

    public ProviderCommand providerId(Long providerId) {
        this.providerId = providerId;
        return this;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getCommandId() {
        return commandId;
    }

    public ProviderCommand commandId(Long commandId) {
        this.commandId = commandId;
        return this;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public Long getCommunicationStandardId() {
        return communicationStandardId;
    }

    public ProviderCommand communicationStandardId(Long communicationStandardId) {
        this.communicationStandardId = communicationStandardId;
        return this;
    }

    public void setCommunicationStandardId(Long communicationStandardId) {
        this.communicationStandardId = communicationStandardId;
    }

    public Long getServiceSecurityId() {
        return serviceSecurityId;
    }

    public ProviderCommand serviceSecurityId(Long serviceSecurityId) {
        this.serviceSecurityId = serviceSecurityId;
        return this;
    }

    public void setServiceSecurityId(Long serviceSecurityId) {
        this.serviceSecurityId = serviceSecurityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderCommand providerCommand = (ProviderCommand) o;
        if (providerCommand.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerCommand.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderCommand{" +
            "id=" + getId() +
            ", endpointUrl='" + getEndpointUrl() + "'" +
            ", code='" + getCode() + "'" +
            ", retryLimit='" + getRetryLimit() + "'" +
            ", retryInterval='" + getRetryInterval() + "'" +
            ", providerId='" + getProviderId() + "'" +
            ", commandId='" + getCommandId() + "'" +
            ", communicationStandardId='" + getCommunicationStandardId() + "'" +
            ", serviceSecurityId='" + getServiceSecurityId() + "'" +
            "}";
    }
}
