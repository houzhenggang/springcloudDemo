package com.sensor.sellCabinet.config;

import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.service.EurekaClientFeign;
import org.springframework.stereotype.Service;

/**
 * 熔断配置类,失败后重新请求
 */
@Service
public class ExampleFeignClientFallback implements EurekaClientFeign {
	@Override
	public String sayHiFromClientEureka(String name) {
		return null;
	}

	@Override
	public R info() {
		return null;
	}
}
