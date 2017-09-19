package com.sigetel.web.service.impl;

import com.sigetel.web.service.ProviderCommandRequestService;
import com.sigetel.web.domain.ProviderCommandRequest;
import com.sigetel.web.repository.ProviderCommandRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ProviderCommandRequest.
 */
@Service
@Transactional
public class ProviderCommandRequestServiceImpl implements ProviderCommandRequestService{

    private final Logger log = LoggerFactory.getLogger(ProviderCommandRequestServiceImpl.class);

    private final ProviderCommandRequestRepository providerCommandRequestRepository;

    public ProviderCommandRequestServiceImpl(ProviderCommandRequestRepository providerCommandRequestRepository) {
        this.providerCommandRequestRepository = providerCommandRequestRepository;
    }

    /**
     * Save a providerCommandRequest.
     *
     * @param providerCommandRequest the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderCommandRequest save(ProviderCommandRequest providerCommandRequest) {
        log.debug("Request to save ProviderCommandRequest : {}", providerCommandRequest);
        return providerCommandRequestRepository.save(providerCommandRequest);
    }

    /**
     *  Get all the providerCommandRequests.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProviderCommandRequest> findAll() {
        log.debug("Request to get all ProviderCommandRequests");
        return providerCommandRequestRepository.findAll();
    }

    /**
     *  Get one providerCommandRequest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProviderCommandRequest findOne(Long id) {
        log.debug("Request to get ProviderCommandRequest : {}", id);
        return providerCommandRequestRepository.findOne(id);
    }

    /**
     *  Delete the  providerCommandRequest by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProviderCommandRequest : {}", id);
        providerCommandRequestRepository.delete(id);
    }
}
