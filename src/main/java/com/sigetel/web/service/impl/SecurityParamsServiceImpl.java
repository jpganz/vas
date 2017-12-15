package com.sigetel.web.service.impl;

import com.sigetel.web.service.SecurityParamsService;
import com.sigetel.web.domain.SecurityParams;
import com.sigetel.web.repository.SecurityParamsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SecurityParams.
 */
@Service
@Transactional
public class SecurityParamsServiceImpl implements SecurityParamsService{

    private final Logger log = LoggerFactory.getLogger(SecurityParamsServiceImpl.class);

    private final SecurityParamsRepository securityParamsRepository;

    public SecurityParamsServiceImpl(SecurityParamsRepository securityParamsRepository) {
        this.securityParamsRepository = securityParamsRepository;
    }

    /**
     * Save a securityParams.
     *
     * @param securityParams the entity to save
     * @return the persisted entity
     */
    @Override
    public SecurityParams save(SecurityParams securityParams) {
        log.debug("Request to save SecurityParams : {}", securityParams);
        return securityParamsRepository.save(securityParams);
    }

    /**
     *  Get all the securityParams.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SecurityParams> findAll() {
        log.debug("Request to get all SecurityParams");
        return securityParamsRepository.findAll();
    }

    /**
     *  Get one securityParams by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SecurityParams findOne(Long id) {
        log.debug("Request to get SecurityParams : {}", id);
        return securityParamsRepository.findOne(id);
    }

    /**
     *  Delete the  securityParams by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SecurityParams : {}", id);
        securityParamsRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecurityParams> findByProviderCommandId(Long id) {
        log.debug("Request to get all SecurityParams");
        return securityParamsRepository.findByProviderCommandId(id);
    }


}
