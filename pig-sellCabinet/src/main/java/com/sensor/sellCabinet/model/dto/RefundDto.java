package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.Refund;
import lombok.Data;

@Data
public class RefundDto extends Refund {
	/**
	 * 场地名字
	 */
	private String teamName;
	/**
	 * 起始日期
	 */
	private String sDate;
	/**
	 * 结束日期
	 */
	private String eDate;
	private String cabinetAddress;
}
