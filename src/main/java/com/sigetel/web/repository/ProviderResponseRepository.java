package com.sigetel.web.repository;

import com.sigetel.web.domain.ProviderResponse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the ProviderResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderResponseRepository extends JpaRepository<ProviderResponse,Long> {

    List<ProviderResponse> findByProviderCommandId(Long id);
}
