package com.sigetel.web.service.impl;

import com.sigetel.web.service.ProviderRequestService;
import com.sigetel.web.domain.ProviderRequest;
import com.sigetel.web.repository.ProviderRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ProviderRequest.
 */
@Service
@Transactional
public class ProviderRequestServiceImpl implements ProviderRequestService{

    private final Logger log = LoggerFactory.getLogger(ProviderRequestServiceImpl.class);

    private final ProviderRequestRepository providerRequestRepository;

    public ProviderRequestServiceImpl(ProviderRequestRepository providerRequestRepository) {
        this.providerRequestRepository = providerRequestRepository;
    }

    /**
     * Save a providerRequest.
     *
     * @param providerRequest the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderRequest save(ProviderRequest providerRequest) {
        log.debug("Request to save ProviderRequest : {}", providerRequest);
        return providerRequestRepository.save(providerRequest);
    }

    /**
     *  Get all the providerRequests.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProviderRequest> findAll() {
        log.debug("Request to get all ProviderRequests");
        return providerRequestRepository.findAll();
    }

    /**
     *  Get one providerRequest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProviderRequest findOne(Long id) {
        log.debug("Request to get ProviderRequest : {}", id);
        return providerRequestRepository.findOne(id);
    }

    /**
     *  Delete the  providerRequest by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProviderRequest : {}", id);
        providerRequestRepository.delete(id);
    }
}
