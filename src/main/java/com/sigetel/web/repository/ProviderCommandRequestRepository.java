package com.sigetel.web.repository;

import com.sigetel.web.domain.ProviderCommandRequest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProviderCommandRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderCommandRequestRepository extends JpaRepository<ProviderCommandRequest,Long> {

}
