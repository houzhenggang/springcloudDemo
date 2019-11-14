package com.sensor.sellCabinet;


import com.pig4cloud.pig.common.security.annotation.EnablePigFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author lengleng
 * @date 2018年06月21日
 * 用户统一管理系统
 */
@EnablePigFeignClients
@SpringCloudApplication
@EnableFeignClients
@EnableScheduling
public class PigSellCabinetApplication {
	public static void main(String[] args) {
			SpringApplication.run(PigSellCabinetApplication.class, args);
	}

}
