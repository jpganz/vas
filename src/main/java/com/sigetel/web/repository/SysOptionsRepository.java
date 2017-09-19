package com.sigetel.web.repository;

import com.sigetel.web.domain.SysOptions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SysOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysOptionsRepository extends JpaRepository<SysOptions,Long> {
    
}
