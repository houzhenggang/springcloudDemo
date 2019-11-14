package com.sensor.sellCabinet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 读取配置文件中的属性
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix="wechat")
@Data
public class WechatConfigurer {
	//appID
	public String appId;
	//appSecret
	public String appSecret;
	//获取openid的路径
	public String openidPath;
	//证书密码
	public String mchId;
	//设置密钥
	public String key;
	//获取回执接口
	public String notifyUrl;
	//微信统一下单
	public String unifiedOrderUrl;
	//项目域名
	public String realmName;
}
