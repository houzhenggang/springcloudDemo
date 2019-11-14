package com.sensor.sellCabinet.model.dto;

import lombok.Data;

@Data
public class ExtractAsk {
	/**
	 * 提取金额
	 */
	private String extractMoney;
	/**
	 * 提取方式|1=微信|2=支付宝
	 */
	private String extractWay;
	/**
	 * 提取备注
	 */
	private String extractRemark;
}
