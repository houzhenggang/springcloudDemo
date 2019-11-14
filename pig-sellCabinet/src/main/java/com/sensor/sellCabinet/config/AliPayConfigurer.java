package com.sensor.sellCabinet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author zengbang
 * @date 2019-09-04 17:05:45
 */

@Configuration
@RefreshScope
@ConfigurationProperties(prefix="alipay")
@Data
public class AliPayConfigurer {
	/***
	 * 卖家支付宝用户号
	 */
	private  String SELLER_ID ;
	/**
	 * 商户appid
	 */
	private  String APP_ID;
	/**
	 * 请求网关地址
	 */
	private  String URL;
	/**
	 * 私钥
	 */
	private  String APP_PRIVATE_KEY ;
	/**
	 * 支付宝公钥
	 */
	private  String ALIPAY_PUBLIC_KEY;
	/**
	 * RSA2
	 */
	private  String SIGNTYPE ;
	/**
	 * 返回格式
	 */
	private  String FORMAT ;
	/**
	 * 编码
	 */
	private  String CHARSET ;

	private  String notifyUrl ;
}
