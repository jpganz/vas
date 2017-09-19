package com.sigetel.web.service;

import com.sigetel.web.domain.ServiceSecurity;
import java.util.List;

/**
 * Service Interface for managing ServiceSecurity.
 */
public interface ServiceSecurityService {

    /**
     * Save a serviceSecurity.
     *
     * @param serviceSecurity the entity to save
     * @return the persisted entity
     */
    ServiceSecurity save(ServiceSecurity serviceSecurity);

    /**
     *  Get all the serviceSecurities.
     *
     *  @return the list of entities
     */
    List<ServiceSecurity> findAll();

    /**
     *  Get the "id" serviceSecurity.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ServiceSecurity findOne(Long id);

    /**
     *  Delete the "id" serviceSecurity.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
