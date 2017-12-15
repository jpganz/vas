package com.sigetel.web.service;

import com.sigetel.web.domain.SecurityParams;
import java.util.List;

/**
 * Service Interface for managing SecurityParams.
 */
public interface SecurityParamsService {

    /**
     * Save a securityParams.
     *
     * @param securityParams the entity to save
     * @return the persisted entity
     */
    SecurityParams save(SecurityParams securityParams);

    /**
     *  Get all the securityParams.
     *
     *  @return the list of entities
     */
    List<SecurityParams> findAll();

    /**
     *  Get the "id" securityParams.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SecurityParams findOne(Long id);

    /**
     *  Delete the "id" securityParams.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<SecurityParams> findByProviderCommandId(Long id);
}
