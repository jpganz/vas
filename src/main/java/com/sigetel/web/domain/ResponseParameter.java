package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ResponseParameter.
 */
@Entity
@Table(name = "response_parameter")
public class ResponseParameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "default_value")
    private String default_value;

    @Column(name = "section")
    private String section;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @ManyToOne
    @JsonIgnore
    private ProviderResponse providerResponse;

    @OneToMany(mappedBy = "responseParameter", fetch = FetchType.EAGER)
    //@JsonIgnore
    private Set<TryResponseParameter> tryResponseParameters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ResponseParameter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public ResponseParameter type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefault_value() {
        return default_value;
    }

    public ResponseParameter default_value(String default_value) {
        this.default_value = default_value;
        return this;
    }

    public void setDefault_value(String default_value) {
        this.default_value = default_value;
    }

    public String getSection() {
        return section;
    }

    public ResponseParameter section(String section) {
        this.section = section;
        return this;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Boolean isIsMandatory() {
        return isMandatory;
    }

    public ResponseParameter isMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
        return this;
    }

    public void setIsMandatory(Boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public ProviderResponse getProviderResponse() {
        return providerResponse;
    }

    public ResponseParameter providerResponse(ProviderResponse providerResponse) {
        this.providerResponse = providerResponse;
        return this;
    }

    public void setProviderResponse(ProviderResponse providerResponse) {
        this.providerResponse = providerResponse;
    }

    public Set<TryResponseParameter> getTryResponseParameters() {
        return tryResponseParameters;
    }

    public ResponseParameter tryResponseParameters(Set<TryResponseParameter> tryResponseParameters) {
        this.tryResponseParameters = tryResponseParameters;
        return this;
    }

    public ResponseParameter addTryResponseParameter(TryResponseParameter tryResponseParameter) {
        this.tryResponseParameters.add(tryResponseParameter);
        tryResponseParameter.setResponseParameter(this);
        return this;
    }

    public ResponseParameter removeTryResponseParameter(TryResponseParameter tryResponseParameter) {
        this.tryResponseParameters.remove(tryResponseParameter);
        tryResponseParameter.setResponseParameter(null);
        return this;
    }

    public void setTryResponseParameters(Set<TryResponseParameter> tryResponseParameters) {
        this.tryResponseParameters = tryResponseParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseParameter responseParameter = (ResponseParameter) o;
        if (responseParameter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), responseParameter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResponseParameter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", default_value='" + getDefault_value() + "'" +
            ", section='" + getSection() + "'" +
            ", isMandatory='" + isIsMandatory() + "'" +
            "}";
    }
}
