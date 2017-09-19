package com.sigetel.web.repository;

import com.sigetel.web.domain.RequestTry;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestTry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestTryRepository extends JpaRepository<RequestTry,Long> {
    
}
