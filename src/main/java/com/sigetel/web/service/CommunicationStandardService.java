package com.sigetel.web.service;

import com.sigetel.web.domain.CommunicationStandard;
import java.util.List;

/**
 * Service Interface for managing CommunicationStandard.
 */
public interface CommunicationStandardService {

    /**
     * Save a communicationStandard.
     *
     * @param communicationStandard the entity to save
     * @return the persisted entity
     */
    CommunicationStandard save(CommunicationStandard communicationStandard);

    /**
     *  Get all the communicationStandards.
     *
     *  @return the list of entities
     */
    List<CommunicationStandard> findAll();

    /**
     *  Get the "id" communicationStandard.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CommunicationStandard findOne(Long id);

    /**
     *  Delete the "id" communicationStandard.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
