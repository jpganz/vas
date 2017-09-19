package com.sigetel.web.service.impl;

import com.sigetel.web.service.ResponseParameterService;
import com.sigetel.web.domain.ResponseParameter;
import com.sigetel.web.repository.ResponseParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ResponseParameter.
 */
@Service
@Transactional
public class ResponseParameterServiceImpl implements ResponseParameterService{

    private final Logger log = LoggerFactory.getLogger(ResponseParameterServiceImpl.class);

    private final ResponseParameterRepository responseParameterRepository;

    public ResponseParameterServiceImpl(ResponseParameterRepository responseParameterRepository) {
        this.responseParameterRepository = responseParameterRepository;
    }

    /**
     * Save a responseParameter.
     *
     * @param responseParameter the entity to save
     * @return the persisted entity
     */
    @Override
    public ResponseParameter save(ResponseParameter responseParameter) {
        log.debug("Request to save ResponseParameter : {}", responseParameter);
        return responseParameterRepository.save(responseParameter);
    }

    /**
     *  Get all the responseParameters.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseParameter> findAll() {
        log.debug("Request to get all ResponseParameters");
        return responseParameterRepository.findAll();
    }

    /**
     *  Get one responseParameter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseParameter findOne(Long id) {
        log.debug("Request to get ResponseParameter : {}", id);
        return responseParameterRepository.findOne(id);
    }

    /**
     *  Delete the  responseParameter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResponseParameter : {}", id);
        responseParameterRepository.delete(id);
    }
}
