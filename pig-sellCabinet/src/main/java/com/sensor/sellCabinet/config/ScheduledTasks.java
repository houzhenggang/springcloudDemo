package com.sensor.sellCabinet.config;

import com.sensor.sellCabinet.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//	@Scheduled(cron = "0/5 * * * * ?")
//	@Scheduled(cron = "0 0/1 * * * ? ")
	@Scheduled(cron = "0 0/30 * * * ? ")
	public void updateBalance() {
		MapperUtil.updateBalance();
		log.info("The time is now {}", dateFormat.format(new Date()));
	}
}
