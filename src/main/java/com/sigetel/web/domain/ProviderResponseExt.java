package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A ProviderResponse.
 */
//@Entity
//@Table(name = "provider_response")
public class ProviderResponseExt implements Serializable {

   ProviderResponse providerResponse;
   ProviderCommand providerCommand;

}
