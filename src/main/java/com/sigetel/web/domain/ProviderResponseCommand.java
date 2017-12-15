package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;


public class ProviderResponseCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("providerResponse")
    private ProviderResponse providerResponse;

    @JsonProperty("providerCommand")
    private ProviderCommand providerCommand;

    public ProviderResponseCommand() {
    }

    public ProviderResponseCommand(ProviderResponse providerResponse, ProviderCommand providerCommand) {
        this.providerResponse = providerResponse;
        this.providerCommand = providerCommand;

    }

    public ProviderResponse getProviderResponse() {
        return providerResponse;
    }

    public void setProviderResponse(ProviderResponse providerResponse) {
        this.providerResponse = providerResponse;
    }

    public ProviderCommand getProviderCommand() {
        return providerCommand;
    }

    public void setProviderCommand(ProviderCommand providerCommand) {
        this.providerCommand = providerCommand;
    }

}
