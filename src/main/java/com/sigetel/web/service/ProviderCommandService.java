package com.sigetel.web.service;

import com.sigetel.web.domain.ProviderCommand;
import java.util.List;

/**
 * Service Interface for managing ProviderCommand.
 */
public interface ProviderCommandService {

    /**
     * Save a providerCommand.
     *
     * @param providerCommand the entity to save
     * @return the persisted entity
     */
    ProviderCommand save(ProviderCommand providerCommand);

    /**
     *  Get all the providerCommands.
     *
     *  @return the list of entities
     */
    List<ProviderCommand> findAll();

    /**
     *  Get the "id" providerCommand.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProviderCommand findOne(Long id);

    /**
     *  Delete the "id" providerCommand.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<ProviderCommand> findByProviderId(Long id);

    List<ProviderCommand> findByProviderCodeAndCommandName(String code, String name);

}
