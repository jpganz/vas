package com.sigetel.web.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigetel.web.domain.AppConfig;
import com.sigetel.web.repository.AppConfigRepository;
import com.sigetel.web.service.AppConfigService;

@Service
@Transactional
public class AppConfigServiceImpl implements AppConfigService{

	private final Logger log = LoggerFactory.getLogger(AppConfigServiceImpl.class);

	private final AppConfigRepository appConfigRepository;

	public AppConfigServiceImpl(AppConfigRepository appConfigRepository) {
		this.appConfigRepository = appConfigRepository;
	}

    @Override
	public AppConfig createAppConfig(AppConfig appConfigDTO) {
		AppConfig appConfig = new AppConfig();
		appConfig.setId(appConfigDTO.getId());
		appConfig.setName(appConfigDTO.getName());
		appConfig.setRoute(appConfigDTO.getRoute());
		appConfig.setValue(appConfigDTO.getValue());
		appConfig.setReport(appConfigDTO.getReport());

		appConfigRepository.save(appConfig);

		return appConfig;
	}

	@Override
	public void updateAppConfig(Long id, String name, String route, String value, Long report) {
		AppConfig appConfig = appConfigRepository.findOneById(id);

		if(appConfig != null) {
			appConfig.setName(name);
			appConfig.setRoute(route);
			appConfig.setValue(value);
			appConfig.setReport(report);

			appConfigRepository.save(appConfig);
		}
	}

	@Override
	public AppConfig updateAppConfig(AppConfig appConfig){

		if(appConfig.getId() != null) {
			appConfigRepository.save(appConfig);

			return appConfig;
		}

		return createAppConfig(appConfig);
	}

	@Override
	public void deleteAppConfig(Long id) {
		AppConfig appConfig = appConfigRepository.findOneById(id);

		if(appConfig.getId() != null) {
			appConfigRepository.delete(appConfig);
		}
	}

	@Override
    @Transactional(readOnly = true)
	public AppConfig findOneById(Long id) {
		return appConfigRepository.findOneById(id);
	}

	@Override
    @Transactional(readOnly = true)
	public List<AppConfig> findAllByOrderByIdAsc() {
		return appConfigRepository.findAllByOrderByIdAsc();
	}

	@Override
	@Transactional(readOnly = true)
	public AppConfig findOneByName(String name) {
		return appConfigRepository.findOneByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public AppConfig findOneByReport(Long report) {
		return appConfigRepository.findOneByReport(report);
	}

	@Override
	@Transactional(readOnly = true)
	public AppConfig findOneByRouteAndReport(String route, Long report) {
		return appConfigRepository.findOneByRouteAndReport(route, report);
	}

	@Override
	@Transactional(readOnly = true)
	public AppConfig findOneByRoute(String route) {
		return appConfigRepository.findOneByRoute(route);
	}

}
