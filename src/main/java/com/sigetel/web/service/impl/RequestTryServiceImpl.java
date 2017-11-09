package com.sigetel.web.service.impl;

import com.sigetel.web.service.RequestTryService;
import com.sigetel.web.domain.RequestTry;
import com.sigetel.web.repository.RequestTryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing RequestTry.
 */
@Service
@Transactional
public class RequestTryServiceImpl implements RequestTryService{

    private final Logger log = LoggerFactory.getLogger(RequestTryServiceImpl.class);

    private final RequestTryRepository requestTryRepository;

    public RequestTryServiceImpl(RequestTryRepository requestTryRepository) {
        this.requestTryRepository = requestTryRepository;
    }

    /**
     * Save a requestTry.
     *
     * @param requestTry the entity to save
     * @return the persisted entity
     */
    @Override
    public RequestTry save(RequestTry requestTry) {
        log.debug("Request to save RequestTry : {}", requestTry);
        return requestTryRepository.save(requestTry);
    }

    /**
     *  Get all the requestTries.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RequestTry> findAll() {
        log.debug("Request to get all RequestTries");
        return requestTryRepository.findAll();
    }

    /**
     *  Get all the requestTries by Status.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RequestTry> findByStatus(int status) {
        return requestTryRepository.findByStatus(status);
    }

    /**
     *  Get one requestTry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RequestTry findOne(Long id) {
        log.debug("Request to get RequestTry : {}", id);
        return requestTryRepository.findOne(id);
    }

    /**
     *  Delete the  requestTry by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestTry : {}", id);
        requestTryRepository.delete(id);
    }
}
