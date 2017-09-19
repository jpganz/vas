package com.sigetel.web.service;

import com.sigetel.web.domain.TryResponseParameter;
import java.util.List;

/**
 * Service Interface for managing TryResponseParameter.
 */
public interface TryResponseParameterService {

    /**
     * Save a tryResponseParameter.
     *
     * @param tryResponseParameter the entity to save
     * @return the persisted entity
     */
    TryResponseParameter save(TryResponseParameter tryResponseParameter);

    /**
     *  Get all the tryResponseParameters.
     *
     *  @return the list of entities
     */
    List<TryResponseParameter> findAll();

    /**
     *  Get the "id" tryResponseParameter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TryResponseParameter findOne(Long id);

    /**
     *  Delete the "id" tryResponseParameter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
