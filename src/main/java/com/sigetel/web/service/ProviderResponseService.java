package com.sigetel.web.service;

import com.sigetel.web.domain.ProviderResponse;
import java.util.List;

/**
 * Service Interface for managing ProviderResponse.
 */
public interface ProviderResponseService {

    /**
     * Save a providerResponse.
     *
     * @param providerResponse the entity to save
     * @return the persisted entity
     */
    ProviderResponse save(ProviderResponse providerResponse);

    /**
     *  Get all the providerResponses.
     *
     *  @return the list of entities
     */
    List<ProviderResponse> findAll();

    /**
     *  Get the "id" providerResponse.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProviderResponse findOne(Long id);

    /**
     *  Delete the "id" providerResponse.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
