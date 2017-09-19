package com.sigetel.web.service;

import com.sigetel.web.domain.ResponseParameter;
import java.util.List;

/**
 * Service Interface for managing ResponseParameter.
 */
public interface ResponseParameterService {

    /**
     * Save a responseParameter.
     *
     * @param responseParameter the entity to save
     * @return the persisted entity
     */
    ResponseParameter save(ResponseParameter responseParameter);

    /**
     *  Get all the responseParameters.
     *
     *  @return the list of entities
     */
    List<ResponseParameter> findAll();

    /**
     *  Get the "id" responseParameter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ResponseParameter findOne(Long id);

    /**
     *  Delete the "id" responseParameter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
