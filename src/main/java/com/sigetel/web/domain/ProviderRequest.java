package com.sigetel.web.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProviderRequest.
 */
@Entity
@Table(name = "provider_request")
public class ProviderRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProviderCommand providerCommand;

    @ManyToOne
    private Request request;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProviderCommand getProviderCommand() {
        return providerCommand;
    }

    public ProviderRequest providerCommand(ProviderCommand providerCommand) {
        this.providerCommand = providerCommand;
        return this;
    }

    public void setProviderCommand(ProviderCommand providerCommand) {
        this.providerCommand = providerCommand;
    }

    public Request getRequest() {
        return request;
    }

    public ProviderRequest request(Request request) {
        this.request = request;
        return this;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderRequest providerRequest = (ProviderRequest) o;
        if (providerRequest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), providerRequest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProviderRequest{" +
            "id=" + getId() +
            "}";
    }
}
