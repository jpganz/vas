package com.sigetel.web.scheduler;

import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SchedulerRequestSender {
	
	public SchedulerRequestSender() {
		
	}

	@Scheduled(cron = "0 0 * ? * *")
	public void callRequestSender() {
		System.out.println(new Date() + " CALL TO REQUEST SENDER! OVAS");
	}
}


