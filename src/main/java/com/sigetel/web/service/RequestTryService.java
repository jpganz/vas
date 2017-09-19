package com.sigetel.web.service;

import com.sigetel.web.domain.RequestTry;
import java.util.List;

/**
 * Service Interface for managing RequestTry.
 */
public interface RequestTryService {

    /**
     * Save a requestTry.
     *
     * @param requestTry the entity to save
     * @return the persisted entity
     */
    RequestTry save(RequestTry requestTry);

    /**
     *  Get all the requestTries.
     *
     *  @return the list of entities
     */
    List<RequestTry> findAll();

    /**
     *  Get the "id" requestTry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RequestTry findOne(Long id);

    /**
     *  Delete the "id" requestTry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
