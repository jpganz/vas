package com.sigetel.web.service.impl;

import com.sigetel.web.service.ProviderResponseService;
import com.sigetel.web.domain.ProviderResponse;
import com.sigetel.web.repository.ProviderResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ProviderResponse.
 */
@Service
@Transactional
public class ProviderResponseServiceImpl implements ProviderResponseService{

    private final Logger log = LoggerFactory.getLogger(ProviderResponseServiceImpl.class);

    private final ProviderResponseRepository providerResponseRepository;

    public ProviderResponseServiceImpl(ProviderResponseRepository providerResponseRepository) {
        this.providerResponseRepository = providerResponseRepository;
    }

    /**
     * Save a providerResponse.
     *
     * @param providerResponse the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderResponse save(ProviderResponse providerResponse) {
        log.debug("Request to save ProviderResponse : {}", providerResponse);
        return providerResponseRepository.save(providerResponse);
    }

    /**
     *  Get all the providerResponses.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProviderResponse> findAll() {
        log.debug("Request to get all ProviderResponses");
        return providerResponseRepository.findAll();
    }

    /**
     *  Get one providerResponse by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProviderResponse findOne(Long id) {
        log.debug("Request to get ProviderResponse : {}", id);
        return providerResponseRepository.findOne(id);
    }

    /**
     *  Delete the  providerResponse by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProviderResponse : {}", id);
        providerResponseRepository.delete(id);
    }
}
