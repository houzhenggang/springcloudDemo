package com.sensor.sellCabinet.config;

import com.pig4cloud.pig.common.security.component.BaseResourceServerConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends BaseResourceServerConfigurerAdapter {

}
