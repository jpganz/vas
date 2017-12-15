package com.sigetel.web.service;

import java.util.List;

import com.sigetel.web.domain.AppConfig;

public interface AppConfigService {

	AppConfig createAppConfig(AppConfig appConfig);

	void updateAppConfig(Long id, String name, String route, String value, Long report);

	AppConfig updateAppConfig(AppConfig appConfig);

	void deleteAppConfig(Long id);

	/***************/

	AppConfig findOneById(Long id);

	List<AppConfig> findAllByOrderByIdAsc();

	AppConfig findOneByName(String name);

	AppConfig findOneByReport(Long report);

	AppConfig findOneByRouteAndReport(String route, Long report);

	AppConfig findOneByRoute(String route);
}
