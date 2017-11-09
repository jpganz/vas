package com.sigetel.web.service.impl;

import com.sigetel.web.domain.RequestTry;
import com.sigetel.web.service.RequestService;
import com.sigetel.web.domain.Request;
import com.sigetel.web.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing Request.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService{

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    /**
     * Save a request.
     *
     * @param request the entity to save
     * @return the persisted entity
     */
    @Override
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        return requestRepository.save(request);
    }

    /**
     *  Get all the requests.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Request> findAll() {
        log.debug("Request to get all Requests");
        List<Request> requests = requestRepository.findAll();
        for(Request request:requests){
            request.getRequestTries();
        }
        return requests;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> findByDatetimeBetween(LocalDate init, LocalDate end) {
        List<Request> requests = requestRepository.findByDateTimeBetween(init, end);
        return requests;
    }

    /**
     *  Get one request by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Request findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        Request request = requestRepository.findOne(id);
        for(RequestTry rtry:request.getRequestTries()){
            rtry.getId();
        }
        return request;
    }

    /**
     *  Delete the  request by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.delete(id);
    }

    /**
     *  Get all requests by status.
     *
     *  @param status Name of the status of the request
     */
    @Override
    public List<Request> findAllByRequestStatus(Long requestStatus) {
        return requestRepository.findByRequestStatus(requestStatus);
    }

}
