package com.sigetel.web.service.impl;

import com.sigetel.web.service.ServiceSecurityService;
import com.sigetel.web.domain.ServiceSecurity;
import com.sigetel.web.repository.ServiceSecurityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ServiceSecurity.
 */
@Service
@Transactional
public class ServiceSecurityServiceImpl implements ServiceSecurityService{

    private final Logger log = LoggerFactory.getLogger(ServiceSecurityServiceImpl.class);

    private final ServiceSecurityRepository serviceSecurityRepository;

    public ServiceSecurityServiceImpl(ServiceSecurityRepository serviceSecurityRepository) {
        this.serviceSecurityRepository = serviceSecurityRepository;
    }

    /**
     * Save a serviceSecurity.
     *
     * @param serviceSecurity the entity to save
     * @return the persisted entity
     */
    @Override
    public ServiceSecurity save(ServiceSecurity serviceSecurity) {
        log.debug("Request to save ServiceSecurity : {}", serviceSecurity);
        return serviceSecurityRepository.save(serviceSecurity);
    }

    /**
     *  Get all the serviceSecurities.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServiceSecurity> findAll() {
        log.debug("Request to get all ServiceSecurities");
        return serviceSecurityRepository.findAll();
    }

    /**
     *  Get one serviceSecurity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ServiceSecurity findOne(Long id) {
        log.debug("Request to get ServiceSecurity : {}", id);
        return serviceSecurityRepository.findOne(id);
    }

    /**
     *  Delete the  serviceSecurity by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceSecurity : {}", id);
        serviceSecurityRepository.delete(id);
    }
}
