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
 * 退款信息表
 *
 * @author chengpan
 * @date 2019-05-15 12:15:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("refund")
public class Refund extends Model<Refund> {
private static final long serialVersionUID = 1L;

    /**
   * 唯一键
   */
    @TableId
    private Integer id;
    /**
   * 类型（0手动退款 1自动退款）
   */
    private String type;
    /**
   * 退款状态（0未退款 1 退款中 2退款完成）
   */
    private String refundStatus;
	/**
	 * 场地id
	 */
	private String teamCode;
	/**
	 * 商品编号
	 */
	private String productCode;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 设备序列号
	 */
	private String cabinetNo;
    /**
   * 订单编号
   */
    private String orderCode;
	/**
   * 通道号
   */
    private String channelNo;
    /**
   * 拒绝原因
   */
    private String refuseReason;
    /**
   * 微信订单号
   */
    private String wechatNumber;
	/**
	 * 支付宝订单号
	 */
	private String alipayTradeno;
    /**
   * 退款金额
   */
    private BigDecimal refundMoney;
    /**
   * 责任人ID
   */
    private String adminId;
    /**
   * 审核结果
   */
    private String approval;
    /**
   * 退款单创建时间
   */
    private LocalDateTime createDate;
    /**
   * 退款时间
   */
    private LocalDateTime refundDate;

}
