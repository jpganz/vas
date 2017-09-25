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
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "communicationStandard", fetch = FetchType.EAGER)
    private Set<ProviderCommand> providerCommands = new HashSet<>();

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
