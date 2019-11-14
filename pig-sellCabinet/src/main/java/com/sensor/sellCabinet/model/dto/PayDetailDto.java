package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.PayDetail;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PayDetailDto extends PayDetail {
	/**
	 * 场地编码
	 */
	private String teamCode;
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
	private String proxyName;
}
