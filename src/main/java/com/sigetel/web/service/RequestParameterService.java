package com.sigetel.web.service;

import com.sigetel.web.domain.RequestParameter;
import java.util.List;

/**
 * Service Interface for managing RequestParameter.
 */
public interface RequestParameterService {

    /**
     * Save a requestParameter.
     *
     * @param requestParameter the entity to save
     * @return the persisted entity
     */
    RequestParameter save(RequestParameter requestParameter);

    /**
     *  Get all the requestParameters.
     *
     *  @return the list of entities
     */
    List<RequestParameter> findAll();

    /**
     *  Get the "id" requestParameter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RequestParameter findOne(Long id);

    /**
     *  Delete the "id" requestParameter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
