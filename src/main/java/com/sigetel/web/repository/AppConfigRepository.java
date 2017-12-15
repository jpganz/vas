package com.sigetel.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigetel.web.domain.AppConfig;

@SuppressWarnings("unused")
@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Long>{

	AppConfig findOneById(Long id);
	
	AppConfig findOneByName(String name);
	
	AppConfig findOneByReport(Long report);
	
	AppConfig findOneByRouteAndReport(String route, Long report);
	
	AppConfig findOneByRoute(String route);
	
	List<AppConfig> findAllByOrderByIdAsc();

}
