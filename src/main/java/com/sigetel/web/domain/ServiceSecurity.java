package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
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

    @NotNull
    @Column(name = "communication_standard_id", nullable = false)
    private Long communicationStandardId;

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

    public Long getCommunicationStandardId() {
        return communicationStandardId;
    }

    public ServiceSecurity communicationStandardId(Long communicationStandardId) {
        this.communicationStandardId = communicationStandardId;
        return this;
    }

    public void setCommunicationStandardId(Long communicationStandardId) {
        this.communicationStandardId = communicationStandardId;
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
            ", communicationStandardId='" + getCommunicationStandardId() + "'" +
            "}";
    }
}
