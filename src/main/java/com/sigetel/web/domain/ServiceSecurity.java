package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ServiceSecurity.
 */
@Entity
@Table(name = "service_security")
public class ServiceSecurity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private CommunicationStandard communicationStandard;

    @OneToMany(mappedBy = "serviceSecurity")
    private Set<SecurityParams> securityParams = new HashSet<>();

    @ManyToOne
    private ProviderCommand providerCommand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ServiceSecurity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommunicationStandard getCommunicationStandard() {
        return communicationStandard;
    }

    public ServiceSecurity communicationStandard(CommunicationStandard communicationStandard) {
        this.communicationStandard = communicationStandard;
        return this;
    }

    public void setCommunicationStandard(CommunicationStandard communicationStandard) {
        this.communicationStandard = communicationStandard;
    }

    public Set<SecurityParams> getSecurityParams() {
        return securityParams;
    }

    public ServiceSecurity securityParams(Set<SecurityParams> securityParams) {
        this.securityParams = securityParams;
        return this;
    }

    public ServiceSecurity addSecurityParams(SecurityParams securityParams) {
        this.securityParams.add(securityParams);
        securityParams.setServiceSecurity(this);
        return this;
    }

    public ServiceSecurity removeSecurityParams(SecurityParams securityParams) {
        this.securityParams.remove(securityParams);
        securityParams.setServiceSecurity(null);
        return this;
    }

    public void setSecurityParams(Set<SecurityParams> securityParams) {
        this.securityParams = securityParams;
    }

    public ProviderCommand getProviderCommand() {
        return providerCommand;
    }

    public ServiceSecurity providerCommand(ProviderCommand providerCommand) {
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
        ServiceSecurity serviceSecurity = (ServiceSecurity) o;
        if (serviceSecurity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceSecurity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceSecurity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
