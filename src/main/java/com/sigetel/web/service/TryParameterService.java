package com.sigetel.web.service;

import com.sigetel.web.domain.TryParameter;
import java.util.List;

/**
 * Service Interface for managing TryParameter.
 */
public interface TryParameterService {

    /**
     * Save a tryParameter.
     *
     * @param tryParameter the entity to save
     * @return the persisted entity
     */
    TryParameter save(TryParameter tryParameter);

    /**
     *  Get all the tryParameters.
     *
     *  @return the list of entities
     */
    List<TryParameter> findAll();

    /**
     *  Get the "id" tryParameter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TryParameter findOne(Long id);

    /**
     *  Delete the "id" tryParameter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     *  Get all the tryParameters by request.
     *
     *  @return the list of entities
     */
    List<TryParameter> findByRequestId(long requestId);
}
