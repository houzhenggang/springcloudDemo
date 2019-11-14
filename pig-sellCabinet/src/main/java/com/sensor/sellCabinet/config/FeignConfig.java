package com.sensor.sellCabinet.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * feign配置类,请求失败后定时继续发起请求
 */
@Configuration
public class FeignConfig {
	@Bean
	public Retryer feignRetryer(){
		return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1),5);
	}

}
