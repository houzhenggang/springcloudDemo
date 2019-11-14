package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.Income;
import lombok.Data;

@Data
public class IncomeDto extends Income {
	private String proxyName;
	private String firstProxyName;
	private String secondProxyName;
	private String thirdProxyName;
	private String fourthProxyName;
	private String fifthProxyName;
	private String teamName;
	private String minusDay;
	/**
	 * 是否自动分润 1是  0不是
	 */
	private String incomeStatus;
	/**
	 * 起始日期
	 */
	private String sDate;
	/**
	 * 结束日期
	 */
	private String eDate;
}
