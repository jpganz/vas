package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Command.
 */
@Entity
@Table(name = "command")
public class Command implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "command")
    @JsonIgnore
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

    public Command name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProviderCommand> getProviderCommands() {
        return providerCommands;
    }

    public Command providerCommands(Set<ProviderCommand> providerCommands) {
        this.providerCommands = providerCommands;
        return this;
    }

    public Command addProviderCommand(ProviderCommand providerCommand) {
        this.providerCommands.add(providerCommand);
        providerCommand.setCommand(this);
        return this;
    }

    public Command removeProviderCommand(ProviderCommand providerCommand) {
        this.providerCommands.remove(providerCommand);
        providerCommand.setCommand(null);
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
        Command command = (Command) o;
        if (command.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), command.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Command{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
