package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CommunicationStandard.
 */
@Entity
@Table(name = "communication_standard")
public class CommunicationStandard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "communicationStandard")
    @JsonIgnore
    private Set<ProviderCommand> providerCommands = new HashSet<>();

    @OneToMany(mappedBy = "communicationStandard")
    @JsonIgnore
    private Set<ServiceSecurity> serviceSecurities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CommunicationStandard name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProviderCommand> getProviderCommands() {
        return providerCommands;
    }

    public CommunicationStandard providerCommands(Set<ProviderCommand> providerCommands) {
        this.providerCommands = providerCommands;
        return this;
    }

    public CommunicationStandard addProviderCommand(ProviderCommand providerCommand) {
        this.providerCommands.add(providerCommand);
        providerCommand.setCommunicationStandard(this);
        return this;
    }

    public CommunicationStandard removeProviderCommand(ProviderCommand providerCommand) {
        this.providerCommands.remove(providerCommand);
        providerCommand.setCommunicationStandard(null);
        return this;
    }

    public void setProviderCommands(Set<ProviderCommand> providerCommands) {
        this.providerCommands = providerCommands;
    }

    public Set<ServiceSecurity> getServiceSecurities() {
        return serviceSecurities;
    }

    public CommunicationStandard serviceSecurities(Set<ServiceSecurity> serviceSecurities) {
        this.serviceSecurities = serviceSecurities;
        return this;
    }

    public CommunicationStandard addServiceSecurity(ServiceSecurity serviceSecurity) {
        this.serviceSecurities.add(serviceSecurity);
        serviceSecurity.setCommunicationStandard(this);
        return this;
    }

    public CommunicationStandard removeServiceSecurity(ServiceSecurity serviceSecurity) {
        this.serviceSecurities.remove(serviceSecurity);
        serviceSecurity.setCommunicationStandard(null);
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
        CommunicationStandard communicationStandard = (CommunicationStandard) o;
        if (communicationStandard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), communicationStandard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommunicationStandard{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
