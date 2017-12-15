package com.sigetel.web.repository;

import com.sigetel.web.domain.ProviderRequest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProviderRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderRequestRepository extends JpaRepository<ProviderRequest,Long> {
    
}
