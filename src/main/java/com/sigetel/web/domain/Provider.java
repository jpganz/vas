package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.*;

/**
 * A Provider.
 */
@Entity
@Table(name = "provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "code", nullable = false)
    private Long code;

    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    //@JsonIgnore
    private List<ProviderCommand> providerCommands = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Provider name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Provider description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCode() {
        return code;
    }

    public Provider code(Long code) {
        this.code = code;
        return this;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public List<ProviderCommand> getProviderCommands() {
        return providerCommands;
    }

    public Provider providerCommands(List<ProviderCommand> providerCommands) {
        this.providerCommands = providerCommands;
        return this;
    }

    public Provider addProviderCommand(ProviderCommand providerCommand) {
        this.providerCommands.add(providerCommand);
        providerCommand.setProvider(this);
        return this;
    }

    public Provider removeProviderCommand(ProviderCommand providerCommand) {
        this.providerCommands.remove(providerCommand);
        providerCommand.setProvider(null);
        return this;
    }

    public void setProviderCommands(List<ProviderCommand> providerCommands) {
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
        Provider provider = (Provider) o;
        if (provider.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provider.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Provider{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
