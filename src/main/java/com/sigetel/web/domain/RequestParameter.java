package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RequestParameter.
 */
@Entity
@Table(name = "request_parameter")
public class RequestParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @Column(name = "default_value")
    private String defaultValue;

    @NotNull
    @Column(name = "section", nullable = false)
    private String section;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @OneToMany(mappedBy = "requestParameter", fetch = FetchType.EAGER)
    //@JsonIgnore
    private Set<TryParameter> tryParameters = new HashSet<>();

    @ManyToOne
    @JsonIgnore
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

    public RequestParameter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public RequestParameter type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public RequestParameter defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getSection() {
        return section;
    }

    public RequestParameter section(String section) {
        this.section = section;
        return this;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Boolean isIsMandatory() {
        return isMandatory;
    }

    public RequestParameter isMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
        return this;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public Set<TryParameter> getTryParameters() {
        return tryParameters;
    }

    public RequestParameter tryParameters(Set<TryParameter> tryParameters) {
        this.tryParameters = tryParameters;
        return this;
    }

    public RequestParameter addTryParameter(TryParameter tryParameter) {
        this.tryParameters.add(tryParameter);
        tryParameter.setRequestParameter(this);
        return this;
    }

    public RequestParameter removeTryParameter(TryParameter tryParameter) {
        this.tryParameters.remove(tryParameter);
        tryParameter.setRequestParameter(null);
        return this;
    }

    public void setTryParameters(Set<TryParameter> tryParameters) {
        this.tryParameters = tryParameters;
    }

    public ProviderCommand getProviderCommand() {
        return providerCommand;
    }

    public RequestParameter providerCommand(ProviderCommand providerCommand) {
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
        RequestParameter requestParameter = (RequestParameter) o;
        if (requestParameter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestParameter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestParameter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            ", section='" + getSection() + "'" +
            ", isMandatory='" + isIsMandatory() + "'" +
            "}";
    }
}
