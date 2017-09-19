package com.sigetel.web.repository;

import com.sigetel.web.domain.ServiceSecurity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ServiceSecurity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceSecurityRepository extends JpaRepository<ServiceSecurity,Long> {
    
}
