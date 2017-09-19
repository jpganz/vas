package com.sigetel.web.repository;

import com.sigetel.web.domain.CommunicationStandard;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CommunicationStandard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommunicationStandardRepository extends JpaRepository<CommunicationStandard,Long> {
    
}
