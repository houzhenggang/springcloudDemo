package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.ExtractCashRecord;
import lombok.Data;

@Data
public class ExtractCashRecordDto extends ExtractCashRecord {
	private String extractPerName;
	/**
	 * 起始日期
	 */
	private String sDate;
	/**
	 * 结束日期
	 */
	private String eDate;
}
