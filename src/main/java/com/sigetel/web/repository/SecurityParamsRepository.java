package com.sigetel.web.repository;

import com.sigetel.web.domain.SecurityParams;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the SecurityParams entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecurityParamsRepository extends JpaRepository<SecurityParams,Long> {

    List<SecurityParams> findByProviderCommandId (Long id);
}
