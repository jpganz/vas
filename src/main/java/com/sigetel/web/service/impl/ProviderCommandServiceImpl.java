package com.sigetel.web.service.impl;

import com.sigetel.web.service.ProviderCommandService;
import com.sigetel.web.domain.ProviderCommand;
import com.sigetel.web.repository.ProviderCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing ProviderCommand.
 */
@Service
@Transactional
public class ProviderCommandServiceImpl implements ProviderCommandService{

    private final Logger log = LoggerFactory.getLogger(ProviderCommandServiceImpl.class);

    private final ProviderCommandRepository providerCommandRepository;

    public ProviderCommandServiceImpl(ProviderCommandRepository providerCommandRepository) {
        this.providerCommandRepository = providerCommandRepository;
    }

    /**
     * Save a providerCommand.
     *
     * @param providerCommand the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderCommand save(ProviderCommand providerCommand) {
        log.debug("Request to save ProviderCommand : {}", providerCommand);
        return providerCommandRepository.save(providerCommand);
    }

    /**
     *  Get all the providerCommands.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProviderCommand> findAll() {
        log.debug("Request to get all ProviderCommands");
        return providerCommandRepository.findAll();
    }

    /**
     *  Get one providerCommand by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProviderCommand findOne(Long id) {
        log.debug("Request to get ProviderCommand : {}", id);
        return providerCommandRepository.findOne(id);
    }

    /**
     *  Delete the  providerCommand by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProviderCommand : {}", id);
        providerCommandRepository.delete(id);
    }
}
