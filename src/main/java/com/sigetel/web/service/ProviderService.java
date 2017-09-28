package com.sigetel.web.service;

import com.sigetel.web.domain.Provider;
import java.util.List;

/**
 * Service Interface for managing Provider.
 */
public interface ProviderService {

    /**
     * Save a provider.
     *
     * @param provider the entity to save
     * @return the persisted entity
     */
    Provider save(Provider provider);

    /**
     *  Get all the providers.
     *
     *  @return the list of entities
     */
    List<Provider> findAll();

    /**
     *  Get the "id" provider.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Provider findOne(Long id);

    /**
     *  Delete the "id" provider.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

}
