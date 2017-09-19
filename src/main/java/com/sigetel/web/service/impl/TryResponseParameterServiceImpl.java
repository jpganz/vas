package com.sigetel.web.service.impl;

import com.sigetel.web.service.TryResponseParameterService;
import com.sigetel.web.domain.TryResponseParameter;
import com.sigetel.web.repository.TryResponseParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TryResponseParameter.
 */
@Service
@Transactional
public class TryResponseParameterServiceImpl implements TryResponseParameterService{

    private final Logger log = LoggerFactory.getLogger(TryResponseParameterServiceImpl.class);

    private final TryResponseParameterRepository tryResponseParameterRepository;

    public TryResponseParameterServiceImpl(TryResponseParameterRepository tryResponseParameterRepository) {
        this.tryResponseParameterRepository = tryResponseParameterRepository;
    }

    /**
     * Save a tryResponseParameter.
     *
     * @param tryResponseParameter the entity to save
     * @return the persisted entity
     */
    @Override
    public TryResponseParameter save(TryResponseParameter tryResponseParameter) {
        log.debug("Request to save TryResponseParameter : {}", tryResponseParameter);
        return tryResponseParameterRepository.save(tryResponseParameter);
    }

    /**
     *  Get all the tryResponseParameters.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TryResponseParameter> findAll() {
        log.debug("Request to get all TryResponseParameters");
        return tryResponseParameterRepository.findAll();
    }

    /**
     *  Get one tryResponseParameter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TryResponseParameter findOne(Long id) {
        log.debug("Request to get TryResponseParameter : {}", id);
        return tryResponseParameterRepository.findOne(id);
    }

    /**
     *  Delete the  tryResponseParameter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TryResponseParameter : {}", id);
        tryResponseParameterRepository.delete(id);
    }
}
