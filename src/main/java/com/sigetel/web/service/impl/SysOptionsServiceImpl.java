package com.sigetel.web.service.impl;

import com.sigetel.web.service.SysOptionsService;
import com.sigetel.web.domain.SysOptions;
import com.sigetel.web.repository.SysOptionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SysOptions.
 */
@Service
@Transactional
public class SysOptionsServiceImpl implements SysOptionsService{

    private final Logger log = LoggerFactory.getLogger(SysOptionsServiceImpl.class);

    private final SysOptionsRepository sysOptionsRepository;

    public SysOptionsServiceImpl(SysOptionsRepository sysOptionsRepository) {
        this.sysOptionsRepository = sysOptionsRepository;
    }

    /**
     * Save a sysOptions.
     *
     * @param sysOptions the entity to save
     * @return the persisted entity
     */
    @Override
    public SysOptions save(SysOptions sysOptions) {
        log.debug("Request to save SysOptions : {}", sysOptions);
        return sysOptionsRepository.save(sysOptions);
    }

    /**
     *  Get all the sysOptions.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SysOptions> findAll() {
        log.debug("Request to get all SysOptions");
        return sysOptionsRepository.findAll();
    }

    /**
     *  Get one sysOptions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SysOptions findOne(Long id) {
        log.debug("Request to get SysOptions : {}", id);
        return sysOptionsRepository.findOne(id);
    }

    /**
     *  Delete the  sysOptions by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SysOptions : {}", id);
        sysOptionsRepository.delete(id);
    }
}
