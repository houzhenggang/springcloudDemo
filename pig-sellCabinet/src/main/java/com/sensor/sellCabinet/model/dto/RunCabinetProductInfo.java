package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.RunCabinetProduct;
import lombok.Data;

@Data
public class RunCabinetProductInfo extends RunCabinetProduct {
	/**
	 * 商品编号
	 */
	private String productCode;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 场地编号
	 */
	private String teamCode;
	/**
	 * 场地名称
	 */
	private String teamName;
	/**
	 * 设备地址
	 */
	private String cabinetAddress;
	/**
	 * (多张照片存储问题)
	 */
	private String productImages;
}

