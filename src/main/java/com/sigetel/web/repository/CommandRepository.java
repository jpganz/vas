package com.sigetel.web.repository;

import com.sigetel.web.domain.Command;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Command entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandRepository extends JpaRepository<Command,Long> {
    
}
