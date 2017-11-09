package com.sigetel.web.service.impl;

import com.sigetel.web.service.RequestParameterService;
import com.sigetel.web.domain.RequestParameter;
import com.sigetel.web.repository.RequestParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestParameter.
 */
@Service
@Transactional
public class RequestParameterServiceImpl implements RequestParameterService{

    private final Logger log = LoggerFactory.getLogger(RequestParameterServiceImpl.class);

    private final RequestParameterRepository requestParameterRepository;

    public RequestParameterServiceImpl(RequestParameterRepository requestParameterRepository) {
        this.requestParameterRepository = requestParameterRepository;
    }

    /**
     * Save a requestParameter.
     *
     * @param requestParameter the entity to save
     * @return the persisted entity
     */
    @Override
    public RequestParameter save(RequestParameter requestParameter) {
        log.debug("Request to save RequestParameter : {}", requestParameter);
        return requestParameterRepository.save(requestParameter);
    }

    /**
     *  Get all the requestParameters.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RequestParameter> findAll() {
        log.debug("Request to get all RequestParameters");
        return requestParameterRepository.findAll();
    }

    /**
     *  Get one requestParameter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RequestParameter findOne(Long id) {
        log.debug("Request to get RequestParameter : {}", id);
        return requestParameterRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestParameter> findByAllProviderCommandId(Long id) {
        log.debug("Request to get RequestParameter : {}", id);
        return requestParameterRepository.findByProviderCommandId(id);
    }

    ;

    /**
     *  Delete the  requestParameter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestParameter : {}", id);
        requestParameterRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public RequestParameter findByName(String name) {
        return requestParameterRepository.findByName(name);
    }

}
