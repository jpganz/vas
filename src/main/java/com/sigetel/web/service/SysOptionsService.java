package com.sigetel.web.service;

import com.sigetel.web.domain.SysOptions;
import java.util.List;

/**
 * Service Interface for managing SysOptions.
 */
public interface SysOptionsService {

    /**
     * Save a sysOptions.
     *
     * @param sysOptions the entity to save
     * @return the persisted entity
     */
    SysOptions save(SysOptions sysOptions);

    /**
     *  Get all the sysOptions.
     *
     *  @return the list of entities
     */
    List<SysOptions> findAll();

    /**
     *  Get the "id" sysOptions.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SysOptions findOne(Long id);

    /**
     *  Delete the "id" sysOptions.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
