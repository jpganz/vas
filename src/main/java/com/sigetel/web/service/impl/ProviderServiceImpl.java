package com.sigetel.web.service.impl;

import com.sigetel.web.domain.ProviderCommand;
import com.sigetel.web.service.ProviderService;
import com.sigetel.web.domain.Provider;
import com.sigetel.web.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Provider.
 */
@Service
@Transactional
public class ProviderServiceImpl implements ProviderService{

    private final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * Save a provider.
     *
     * @param provider the entity to save
     * @return the persisted entity
     */
    @Override
    public Provider save(Provider provider) {
        log.debug("Request to save Provider : {}", provider);
        return providerRepository.save(provider);
    }

    /**
     *  Get all the providers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Provider> findAll() {
        log.debug("Request to get all Providers");
        return providerRepository.findAll();
    }

    /**
     *  Get one provider by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Provider findOne(Long id) {
        log.debug("Request to get Provider : {}", id);
        Provider provider = providerRepository.findOne(id);
        for(ProviderCommand command:provider.getProviderCommands()){
            command.getId();
        }
        return provider;
    }
    /**
     *  Delete the  provider by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Provider : {}", id);
        providerRepository.delete(id);
    }

    @Override
    public Provider findByCode(String code) {
        return providerRepository.findByCode(code);
    }
/*
    @Override
    @Transactional(readOnly = true)
    public Provider findByIdAndProviderCommandId(Long id, Long commandId) {
        log.debug("Request to get Provider : {}", id);
        return providerRepository.findByIdAndProviderCommandsId(id, commandId);
    }

  */
}
