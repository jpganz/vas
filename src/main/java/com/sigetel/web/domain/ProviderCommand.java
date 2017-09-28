package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "email_address_to_notify")
    private String emailAddressToNotify;

    @Column(name = "add_to_retry")
    private Boolean addToRetry;

    @Column(name = "email_notify")
    private Integer emailNotify;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Provider provider;

    @ManyToOne
    private CommunicationStandard communicationStandard;

    @OneToMany(mappedBy = "providerCommand")
    //@JsonIgnore
    private Set<ProviderResponse> providerResponses = new HashSet<>();

    @OneToMany(mappedBy = "providerCommand")
    @JsonIgnore
    private Set<RequestParameter> requestParameters = new HashSet<>();

    @ManyToOne
    private Command command;

    @OneToMany(mappedBy = "providerCommand")
    @JsonIgnore
    private Set<ServiceSecurity> serviceSecurities = new HashSet<>();

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

    public String getEmailAddressToNotify() {
        return emailAddressToNotify;
    }

    public ProviderCommand emailAddressToNotify(String emailAddressToNotify) {
        this.emailAddressToNotify = emailAddressToNotify;
        return this;
    }

    public void setEmailAddressToNotify(String emailAddressToNotify) {
        this.emailAddressToNotify = emailAddressToNotify;
    }

    public Boolean isAddToRetry() {
        return addToRetry;
    }

    public ProviderCommand addToRetry(Boolean addToRetry) {
        this.addToRetry = addToRetry;
        return this;
    }

    public void setAddToRetry(Boolean addToRetry) {
        this.addToRetry = addToRetry;
    }

    public Integer getEmailNotify() {
        return emailNotify;
    }

    public ProviderCommand emailNotify(Integer emailNotify) {
        this.emailNotify = emailNotify;
        return this;
    }

    public void setEmailNotify(Integer emailNotify) {
        this.emailNotify = emailNotify;
    }

    public Provider getProvider() {
        return provider;
    }

    public ProviderCommand provider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public CommunicationStandard getCommunicationStandard() {
        return communicationStandard;
    }

    public ProviderCommand communicationStandard(CommunicationStandard communicationStandard) {
        this.communicationStandard = communicationStandard;
        return this;
    }

    public void setCommunicationStandard(CommunicationStandard communicationStandard) {
        this.communicationStandard = communicationStandard;
    }

    public Set<ProviderResponse> getProviderResponses() {
        return providerResponses;
    }

    public ProviderCommand providerResponses(Set<ProviderResponse> providerResponses) {
        this.providerResponses = providerResponses;
        return this;
    }

    public ProviderCommand addProviderResponse(ProviderResponse providerResponse) {
        this.providerResponses.add(providerResponse);
        providerResponse.setProviderCommand(this);
        return this;
    }

    public ProviderCommand removeProviderResponse(ProviderResponse providerResponse) {
        this.providerResponses.remove(providerResponse);
        providerResponse.setProviderCommand(null);
        return this;
    }

    public void setProviderResponses(Set<ProviderResponse> providerResponses) {
        this.providerResponses = providerResponses;
    }

    public Set<RequestParameter> getRequestParameters() {
        return requestParameters;
    }

    public ProviderCommand requestParameters(Set<RequestParameter> requestParameters) {
        this.requestParameters = requestParameters;
        return this;
    }

    public ProviderCommand addRequestParameter(RequestParameter requestParameter) {
        this.requestParameters.add(requestParameter);
        requestParameter.setProviderCommand(this);
        return this;
    }

    public ProviderCommand removeRequestParameter(RequestParameter requestParameter) {
        this.requestParameters.remove(requestParameter);
        requestParameter.setProviderCommand(null);
        return this;
    }

    public void setRequestParameters(Set<RequestParameter> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public Command getCommand() {
        return command;
    }

    public ProviderCommand command(Command command) {
        this.command = command;
        return this;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Set<ServiceSecurity> getServiceSecurities() {
        return serviceSecurities;
    }

    public ProviderCommand serviceSecurities(Set<ServiceSecurity> serviceSecurities) {
        this.serviceSecurities = serviceSecurities;
        return this;
    }

    public ProviderCommand addServiceSecurity(ServiceSecurity serviceSecurity) {
        this.serviceSecurities.add(serviceSecurity);
        serviceSecurity.setProviderCommand(this);
        return this;
    }

    public ProviderCommand removeServiceSecurity(ServiceSecurity serviceSecurity) {
        this.serviceSecurities.remove(serviceSecurity);
        serviceSecurity.setProviderCommand(null);
        return this;
    }

    public void setServiceSecurities(Set<ServiceSecurity> serviceSecurities) {
        this.serviceSecurities = serviceSecurities;
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
            ", emailAddressToNotify='" + getEmailAddressToNotify() + "'" +
            ", addToRetry='" + isAddToRetry() + "'" +
            ", emailNotify='" + getEmailNotify() + "'" +
            "}";
    }
}
