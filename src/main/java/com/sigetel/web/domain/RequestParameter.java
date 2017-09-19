package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
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

    @NotNull
    @Column(name = "provider_command_id", nullable = false)
    private Long providerCommandId;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

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

    public Long getProviderCommandId() {
        return providerCommandId;
    }

    public RequestParameter providerCommandId(Long providerCommandId) {
        this.providerCommandId = providerCommandId;
        return this;
    }

    public void setProviderCommandId(Long providerCommandId) {
        this.providerCommandId = providerCommandId;
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
            ", providerCommandId='" + getProviderCommandId() + "'" +
            ", isMandatory='" + isIsMandatory() + "'" +
            "}";
    }
}
