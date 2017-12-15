package com.sigetel.web.service;

import com.sigetel.web.domain.ProviderRequest;
import java.util.List;

/**
 * Service Interface for managing ProviderRequest.
 */
public interface ProviderRequestService {

    /**
     * Save a providerRequest.
     *
     * @param providerRequest the entity to save
     * @return the persisted entity
     */
    ProviderRequest save(ProviderRequest providerRequest);

    /**
     *  Get all the providerRequests.
     *
     *  @return the list of entities
     */
    List<ProviderRequest> findAll();

    /**
     *  Get the "id" providerRequest.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProviderRequest findOne(Long id);

    /**
     *  Delete the "id" providerRequest.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
