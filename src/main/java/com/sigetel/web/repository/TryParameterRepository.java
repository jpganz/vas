package com.sigetel.web.repository;

import com.sigetel.web.domain.TryParameter;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the TryParameter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TryParameterRepository extends JpaRepository<TryParameter,Long> {

    List<TryParameter> findByRequestId(long id);
}
