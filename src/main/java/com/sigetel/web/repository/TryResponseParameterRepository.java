package com.sigetel.web.repository;

import com.sigetel.web.domain.TryResponseParameter;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TryResponseParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TryResponseParameterRepository extends JpaRepository<TryResponseParameter,Long> {
    
}
