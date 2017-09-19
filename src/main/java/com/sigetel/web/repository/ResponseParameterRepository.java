package com.sigetel.web.repository;

import com.sigetel.web.domain.ResponseParameter;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ResponseParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponseParameterRepository extends JpaRepository<ResponseParameter,Long> {
    
}
