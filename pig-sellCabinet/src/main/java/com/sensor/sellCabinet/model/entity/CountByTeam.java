package com.sensor.sellCabinet.model.entity;

import lombok.Data;

@Data
public class CountByTeam {
	//主键
	private Integer id;
	//用户id
	private String userId;
	//柜子编号
	private String cabinetNo;
	//货柜地址
	private String cabinetAddress;
	//代理商名称
	private String proxyName;
	//运营天数
	private String days;
	//场地编码
	private String teamCode;
	//场地名称
	private String teamName;
	//设备数量
	private String cabinetCount;
	//订单量
	private String totolOrder;
	//订单收益
	private String payAmountsum;
	//开柜率
	private String openPr;
	//开柜率 起
	private String sopenPr;
	//开柜率 止
	private String eopenPr;
	//客单价
	private String singlePrice;
	//累计投放天数（台*天）
	private String cabinetDays;
	//平均  元/台/天
	private String singleAmount;
	//平均  元/台/天  起
	private String ssingleAmount;
	//平均  元/台/天  止
	private String esingleAmount;
}
