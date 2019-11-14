package com.sensor.sellCabinet.service;

import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.config.ExampleFeignClientFallback;
import com.sensor.sellCabinet.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign接口
 */
//@Component
@FeignClient(value = ServiceNameConstants.UMPS_SERVICE,configuration  = FeignConfig.class)
public interface EurekaClientFeign {
	@GetMapping(value = "/hi")
	String sayHiFromClientEureka(@RequestParam(value = "name")String name);

	@GetMapping(value = {"user/info"})
	R info();
}
