package com.sigetel.web.service;

import com.sigetel.web.domain.Command;
import java.util.List;

/**
 * Service Interface for managing Command.
 */
public interface CommandService {

    /**
     * Save a command.
     *
     * @param command the entity to save
     * @return the persisted entity
     */
    Command save(Command command);

    /**
     *  Get all the commands.
     *
     *  @return the list of entities
     */
    List<Command> findAll();

    /**
     *  Get the "id" command.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Command findOne(Long id);

    /**
     *  Delete the "id" command.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
