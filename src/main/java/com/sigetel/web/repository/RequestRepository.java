package com.sigetel.web.repository;

import com.sigetel.web.domain.Request;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


/**
 * Spring Data JPA repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {

    List<Request> findByDateTimeBetween(LocalDate init, LocalDate end);

    List<Request> findByRequestStatus(Long status);
}
