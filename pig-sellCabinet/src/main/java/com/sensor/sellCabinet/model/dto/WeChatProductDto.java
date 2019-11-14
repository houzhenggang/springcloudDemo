package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.CabinetSpecs;
import lombok.Data;

import java.util.List;

@Data
public class WeChatProductDto{
	/**
	 * 场地编码
	 */
	private String teamCode;
	/**
	 * 规格集合
	 */
	private List<CabinetSpecs> cabinetSpecsList;
	/**
	 * 货道商品集合
	 */
	private List<RunCabinetProductInfo> runCabinetProductDtoList;
}
