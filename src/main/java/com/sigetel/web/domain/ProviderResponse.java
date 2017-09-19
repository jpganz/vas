package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
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

    @NotNull
    @Column(name = "provider_command_id", nullable = false)
    private Long providerCommandId;

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

    public Long getProviderCommandId() {
        return providerCommandId;
    }

    public ProviderResponse providerCommandId(Long providerCommandId) {
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
            ", providerCommandId='" + getProviderCommandId() + "'" +
            "}";
    }
}
