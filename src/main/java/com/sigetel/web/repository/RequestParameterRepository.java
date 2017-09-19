package com.sigetel.web.repository;

import com.sigetel.web.domain.RequestParameter;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RequestParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestParameterRepository extends JpaRepository<RequestParameter,Long> {
    
}
