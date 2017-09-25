package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProviderResponse.
 */
@Entity
@Table(name = "provider_response")
public class ProviderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_notify")
    private String emailNotify;

    @Column(name = "email_address_to_notify")
    private String emailAddressToNotify;

    @Column(name = "add_to_retry")
    private Boolean addToRetry;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @ManyToOne
    @JsonIgnore
    private ProviderCommand providerCommand;

    @OneToMany(mappedBy = "providerResponse", fetch = FetchType.EAGER)
    //@JsonIgnore
    private Set<ResponseParameter> responseParameters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailNotify() {
        return emailNotify;
    }

    public ProviderResponse emailNotify(String emailNotify) {
        this.emailNotify = emailNotify;
        return this;
    }

    public void setEmailNotify(String emailNotify) {
        this.emailNotify = emailNotify;
    }

    public String getEmailAddressToNotify() {
        return emailAddressToNotify;
    }

    public ProviderResponse emailAddressToNotify(String emailAddressToNotify) {
        this.emailAddressToNotify = emailAddressToNotify;
        return this;
    }

    public void setEmailAddressToNotify(String emailAddressToNotify) {
        this.emailAddressToNotify = emailAddressToNotify;
    }

    public Boolean isAddToRetry() {
        return addToRetry;
    }

    public ProviderResponse addToRetry(Boolean addToRetry) {
        this.addToRetry = addToRetry;
        return this;
    }

    public void setAddToRetry(Boolean addToRetry) {
        this.addToRetry = addToRetry;
    }

    public String getType() {
        return type;
    }

    public ProviderResponse type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProviderCommand getProviderCommand() {
        return providerCommand;
    }

    public ProviderResponse providerCommand(ProviderCommand providerCommand) {
        this.providerCommand = providerCommand;
        return this;
    }

    public void setProviderCommand(ProviderCommand providerCommand) {
        this.providerCommand = providerCommand;
    }

    public Set<ResponseParameter> getResponseParameters() {
        return responseParameters;
    }

    public ProviderResponse responseParameters(Set<ResponseParameter> responseParameters) {
        this.responseParameters = responseParameters;
        return this;
    }

    public ProviderResponse addResponseParameter(ResponseParameter responseParameter) {
        this.responseParameters.add(responseParameter);
        responseParameter.setProviderResponse(this);
        return this;
    }

    public ProviderResponse removeResponseParameter(ResponseParameter responseParameter) {
        this.responseParameters.remove(responseParameter);
        responseParameter.setProviderResponse(null);
        return this;
    }

    public void setResponseParameters(Set<ResponseParameter> responseParameters) {
        this.responseParameters = responseParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderResponse providerResponse = (ProviderResponse) o;
        if (providerResponse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerResponse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderResponse{" +
            "id=" + getId() +
            ", emailNotify='" + getEmailNotify() + "'" +
            ", emailAddressToNotify='" + getEmailAddressToNotify() + "'" +
            ", addToRetry='" + isAddToRetry() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
