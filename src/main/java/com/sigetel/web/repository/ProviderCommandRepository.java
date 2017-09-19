package com.sigetel.web.repository;

import com.sigetel.web.domain.ProviderCommand;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProviderCommand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderCommandRepository extends JpaRepository<ProviderCommand,Long> {
    
}
