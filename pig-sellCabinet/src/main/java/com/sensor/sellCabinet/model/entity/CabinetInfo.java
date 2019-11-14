package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 货柜信息
 *
 * @author chengpan
 * @date 2019-04-03 11:21:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cabinet_info")
public class CabinetInfo extends Model<CabinetInfo> {
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一码
	 */
	@TableId
	private String id;
	/**
	 * 货柜编号
	 */
	private String cabinetNo;
	/**
	 * 场地编码
	 */
	private String teamCode;
	/**
	 * 货柜规格编号
	 */
	private String specsNo;
	private String specs;
	/**
	 * 蓝牙ID商家
	 */
	private String bluetoothBusiness;
	/**
	 * 蓝牙ID用户
	 */
	private String bluetoothUser;
	/**
	 * 当前状态|0=待出库|1=已出库|2=已驳回
	 */
	private String status;
	private String isEmpty;
	/**
	 * 通道数
	 */
	private Integer channelNum;
	/**
	 * 货柜拥有者
	 */
	private String operator;
	/**
	 * 入库批次号
	 */
	private String importPch;
	/**
	 * 货柜别名
	 */
	private String cabinetAlias;
	/**
	 * 货柜代理商
	 */
	private String proxyBusiness;
	/**
	 * 电量
	 */
	private String battery;
	/**
	 * 入库时间
	 */
	private LocalDateTime importDate;
	/**
	 * 电量更新时间
	 */
	private LocalDateTime reportDate;
	/**
	 * 设备地址
	 */
	private String cabinetAddress;

}
