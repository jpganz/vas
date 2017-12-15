package com.sigetel.web.repository;

import com.sigetel.web.domain.ConfigReportSend;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unused")
@Repository
public interface ConfigReportSendRepository extends JpaRepository<ConfigReportSend, Long>{
	
	ConfigReportSend findOneById(Long id);
	ConfigReportSend findOneByIdReport(Long id_report);
	
	List<ConfigReportSend> findAllByOrderByIdAsc();

	@Transactional
	@Modifying
	@Query("update ConfigReportSend crs set crs.lastSend = ?1 where crs.id = ?2")
	void updateLastSend(Instant lastSend, Long id);
}
