package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.PayInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PayInfoV extends PayInfo {
	/**
	 * 场地名字
	 */
	private String teamName;
	private String hotline;
	private String proxyName;
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
