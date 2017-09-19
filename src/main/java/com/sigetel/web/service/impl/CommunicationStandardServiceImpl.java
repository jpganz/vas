package com.sigetel.web.service.impl;

import com.sigetel.web.service.CommunicationStandardService;
import com.sigetel.web.domain.CommunicationStandard;
import com.sigetel.web.repository.CommunicationStandardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing CommunicationStandard.
 */
@Service
@Transactional
public class CommunicationStandardServiceImpl implements CommunicationStandardService{

    private final Logger log = LoggerFactory.getLogger(CommunicationStandardServiceImpl.class);

    private final CommunicationStandardRepository communicationStandardRepository;

    public CommunicationStandardServiceImpl(CommunicationStandardRepository communicationStandardRepository) {
        this.communicationStandardRepository = communicationStandardRepository;
    }

    /**
     * Save a communicationStandard.
     *
     * @param communicationStandard the entity to save
     * @return the persisted entity
     */
    @Override
    public CommunicationStandard save(CommunicationStandard communicationStandard) {
        log.debug("Request to save CommunicationStandard : {}", communicationStandard);
        return communicationStandardRepository.save(communicationStandard);
    }

    /**
     *  Get all the communicationStandards.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommunicationStandard> findAll() {
        log.debug("Request to get all CommunicationStandards");
        return communicationStandardRepository.findAll();
    }

    /**
     *  Get one communicationStandard by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CommunicationStandard findOne(Long id) {
        log.debug("Request to get CommunicationStandard : {}", id);
        return communicationStandardRepository.findOne(id);
    }

    /**
     *  Delete the  communicationStandard by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommunicationStandard : {}", id);
        communicationStandardRepository.delete(id);
    }
}
