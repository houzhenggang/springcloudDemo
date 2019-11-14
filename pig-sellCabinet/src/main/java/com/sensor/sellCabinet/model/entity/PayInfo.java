package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实收汇总表
 *
 * @author chengpan
 * @date 2019-04-23 16:20:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_info")
public class PayInfo extends Model<PayInfo> {
private static final long serialVersionUID = 1L;

    /**
   * 唯一键
   */
    @TableId
    private Integer id;
	/**
	 * 实收总账编号
	 */
	private String orderCode;
    /**
   * 用户id
   */
    private String userId;
    /**
   * 订单金额
   */
    private BigDecimal orderPrice;
	/**
	 * 微信订单号
	 */
	private String wechatNumber;
	/**
	 * 支付宝订单号
	 */
	private String alipayTradeno;
	/**
	 * 场地id
	 */
	private String teamCode;
    /**
   * 设备序列号
   */
    private String cabinetNo;
    /**
   * 创建时间
   */
    private LocalDateTime createDate;
    /**
   * 订单付款状态（0未付款 1已付款）
   */
    private String orderStatus;
    /**
   * 实付金额
   */
    private BigDecimal payAmount;
    /**
   * 设备状态
   */
    private String cabinetStatus;
    /**
   * 付款方式
   */
    private String paySource;
    /**
   * 订单类型
   */
    private String billType;
    /**
   * 第三方订单号
   */
    private Integer transId;
    /**
   * 付款时间
   */
    private LocalDateTime payDate;
    /**
   * 蓝牙是否回写
   */
    private String blueIsWrite;
	/**
	 * 主题
	 */
	private String body;
	/**
	 * 详情
	 */
    private String detail;

}
