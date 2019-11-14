package com.sensor.sellCabinet.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaleCount {
	/**
	 * 用户id
	 */
	Integer userId;
	/**
	 * 累计销售额
	 */
	BigDecimal totolSale;
	/**
	 * 月销售额
	 */
	BigDecimal monthSale;
	/**
	 * 昨日销售额
	 */
	BigDecimal yesterdaySale;
	/**
	 * 今日销售额
	 */
	BigDecimal todaySale;
	/**
	 * 累计订单数
	 */
	Integer totolBill;
	/**
	 * 月订单数
	 */
	Integer monthBill;
	/**
	 * 昨日订单数
	 */
	Integer yesterdayBill;
	/**
	 * 今日订单数
	 */
	Integer todayBill;
	/**
	 * 总盈利额
	 */
	BigDecimal totolExtract;
	/**
	 * 已提现额
	 */
	BigDecimal extracted;
	/**
	 * 待分润额
	 */
	BigDecimal noExtracted;

}
