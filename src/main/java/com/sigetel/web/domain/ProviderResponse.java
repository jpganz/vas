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

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @Column(name = "add_to_retry")
    private Integer addToRetry;

    @ManyToOne
    private ProviderCommand providerCommand;

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

    public Integer getAddToRetry() {
        return addToRetry;
    }

    public ProviderResponse addToRetry(Integer addToRetry) {
        this.addToRetry = addToRetry;
        return this;
    }

    public void setAddToRetry(Integer addToRetry) {
        this.addToRetry = addToRetry;
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
            ", type='" + getType() + "'" +
            ", addToRetry='" + getAddToRetry() + "'" +
            "}";
    }
}
