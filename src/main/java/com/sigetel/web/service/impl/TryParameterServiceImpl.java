package com.sigetel.web.service.impl;

import com.sigetel.web.service.TryParameterService;
import com.sigetel.web.domain.TryParameter;
import com.sigetel.web.repository.TryParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TryParameter.
 */
@Service
@Transactional
public class TryParameterServiceImpl implements TryParameterService{

    private final Logger log = LoggerFactory.getLogger(TryParameterServiceImpl.class);

    private final TryParameterRepository tryParameterRepository;

    public TryParameterServiceImpl(TryParameterRepository tryParameterRepository) {
        this.tryParameterRepository = tryParameterRepository;
    }

    /**
     * Save a tryParameter.
     *
     * @param tryParameter the entity to save
     * @return the persisted entity
     */
    @Override
    public TryParameter save(TryParameter tryParameter) {
        log.debug("Request to save TryParameter : {}", tryParameter);
        return tryParameterRepository.save(tryParameter);
    }

    /**
     *  Get all the tryParameters.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TryParameter> findAll() {
        log.debug("Request to get all TryParameters");
        return tryParameterRepository.findAll();
    }

    /**
     *  Get one tryParameter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TryParameter findOne(Long id) {
        log.debug("Request to get TryParameter : {}", id);
        return tryParameterRepository.findOne(id);
    }

    /**
     *  Delete the  tryParameter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TryParameter : {}", id);
        tryParameterRepository.delete(id);
    }
}
