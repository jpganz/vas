package com.sigetel.web.service.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigetel.web.domain.ConfigReportSend;
import com.sigetel.web.repository.ConfigReportSendRepository;
import com.sigetel.web.service.ConfigReportSendService;

@Service
@Transactional
public class ConfigReportSendServiceImpl implements ConfigReportSendService{
	
	private final Logger log = LoggerFactory.getLogger(ConfigReportSendServiceImpl.class);
	
	private final ConfigReportSendRepository configReportSendRepository;
	
	public ConfigReportSendServiceImpl(ConfigReportSendRepository configReportSendRepository) {
		this.configReportSendRepository = configReportSendRepository;
	}

	@Override
	public ConfigReportSend createConfigReportSend(ConfigReportSend configReportSendDTO) {
		
		ConfigReportSend configReportSend = new ConfigReportSend();
		configReportSend.setId(configReportSendDTO.getId());
		configReportSend.setIdReport(configReportSendDTO.getIdReport());
		configReportSend.setFormatType(configReportSendDTO.getFormatType());
		configReportSend.setMailCopy(configReportSendDTO.getMailCopy());
		configReportSend.setLastSend(configReportSendDTO.getLastSend());
		configReportSend.setTime(configReportSendDTO.getTime());

		configReportSendRepository.save(configReportSend);

		log.debug("Created Information for Report sender configuration: {}", configReportSend);

		return configReportSend;
	}
	
	@Override
	public ConfigReportSend createConfigReportSend(Long id, Long idReport, Instant time, String formatType,
			String mailCopy) {
		ConfigReportSend configReportSend = new ConfigReportSend();
		configReportSend.setId(id);
		configReportSend.setIdReport(idReport);
		configReportSend.setFormatType(formatType);
		configReportSend.setMailCopy(mailCopy);
		configReportSend.setTime(time);

		configReportSendRepository.save(configReportSend);

		log.debug("Created Information for Report sender configuration: {}", configReportSend);

		return configReportSend;
	}

	@Override
	public void updateConfigReportSend(Long id, Long idReport, Instant time, String formatType, String mailCopy,
			Instant lastSend) {
		
		ConfigReportSend configReportSend =  configReportSendRepository.findOneById(id);
		
		if(configReportSend != null) {
			configReportSend.setFormatType(formatType);
			configReportSend.setIdReport(idReport);
			configReportSend.setMailCopy(mailCopy);
			configReportSend.setTime(time);
			configReportSend.setLastSend(lastSend);
			
			configReportSendRepository.save(configReportSend);
			
			log.debug("Changed Information for Report sender configuration: {}", configReportSend);
		}
		
	}

	@Override
	public ConfigReportSend updateConfigReportSend(ConfigReportSend configReportSendVM) {

		if(configReportSendVM.getId() != null) {
			configReportSendRepository.save(configReportSendVM);
			
			return configReportSendVM;
		}
		
		return createConfigReportSend(configReportSendVM);
	}

	@Override
	public void deleteConfigReportSend(Long id) {

		ConfigReportSend configReportSend = configReportSendRepository.findOneById(id);
		
		if(configReportSend.getId() != null) {
			configReportSendRepository.delete(configReportSend);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigReportSend findOneById(Long id) {
		
		return configReportSendRepository.findOneById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ConfigReportSend> findAllByOrderByIdAsc() {
		return configReportSendRepository.findAllByOrderByIdAsc();
	}
}
