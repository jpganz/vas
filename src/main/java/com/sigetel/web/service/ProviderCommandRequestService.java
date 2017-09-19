package com.sigetel.web.service;

import com.sigetel.web.domain.ProviderCommandRequest;
import java.util.List;

/**
 * Service Interface for managing ProviderCommandRequest.
 */
public interface ProviderCommandRequestService {

    /**
     * Save a providerCommandRequest.
     *
     * @param providerCommandRequest the entity to save
     * @return the persisted entity
     */
    ProviderCommandRequest save(ProviderCommandRequest providerCommandRequest);

    /**
     *  Get all the providerCommandRequests.
     *
     *  @return the list of entities
     */
    List<ProviderCommandRequest> findAll();

    /**
     *  Get the "id" providerCommandRequest.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProviderCommandRequest findOne(Long id);

    /**
     *  Delete the "id" providerCommandRequest.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
