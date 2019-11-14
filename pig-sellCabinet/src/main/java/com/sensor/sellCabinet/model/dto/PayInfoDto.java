package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.PayDetail;
import com.sensor.sellCabinet.model.entity.PayInfo;
import lombok.Data;

import java.util.List;

/**
 * 订单返回实体类
 */
@Data
public class PayInfoDto {
	/**
	 * 订单表主表
	 */
	private PayInfo payInfo;

	/**
	 * 订单表详情表
	 */
	private List<PayDetail> payDetailList;

}
