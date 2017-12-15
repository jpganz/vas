package com.sigetel.web.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SecurityParams.
 */
@Entity
@Table(name = "security_params")
public class SecurityParams implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "field", nullable = false)
    private String field;

    @Column(name = "jhi_value")
    private String value;

    @Column(name = "section")
    private String section;

    @ManyToOne
    @JsonIgnore
    private ServiceSecurity serviceSecurity;

    @ManyToOne
    @JsonIgnore
    private ProviderCommand providerCommand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public SecurityParams field(String field) {
        this.field = field;
        return this;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public SecurityParams value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSection() {
        return section;
    }

    public SecurityParams section(String section) {
        this.section = section;
        return this;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public ServiceSecurity getServiceSecurity() {
        return serviceSecurity;
    }

    public SecurityParams serviceSecurity(ServiceSecurity serviceSecurity) {
        this.serviceSecurity = serviceSecurity;
        return this;
    }

    public ProviderCommand getProviderCommand() {
        return providerCommand;
    }

    public void setProviderCommand(ProviderCommand providerCommand) {
        this.providerCommand = providerCommand;
    }

    public void setServiceSecurity(ServiceSecurity serviceSecurity) {
        this.serviceSecurity = serviceSecurity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecurityParams securityParams = (SecurityParams) o;
        if (securityParams.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), securityParams.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SecurityParams{" +
            "id=" + getId() +
            ", field='" + getField() + "'" +
            ", value='" + getValue() + "'" +
            ", section='" + getSection() + "'" +
            "}";
    }
}
