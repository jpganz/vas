package com.sigetel.web.service;

import java.time.Instant;
import java.util.List;

import com.sigetel.web.domain.ConfigReportSend;

public interface ConfigReportSendService {

	ConfigReportSend createConfigReportSend(ConfigReportSend configReportSend);
	
	ConfigReportSend createConfigReportSend(Long id, Long idReport, Instant time, String formatType, 
			String mailCopy);

	void updateConfigReportSend(Long id, Long idReport, Instant time, String formatType, 
			String mailCopy, Instant lastSend);

	ConfigReportSend updateConfigReportSend(ConfigReportSend configReportSend);

	void deleteConfigReportSend(Long id);

	/***************/

	ConfigReportSend findOneById(Long id);

	List<ConfigReportSend> findAllByOrderByIdAsc();
}
