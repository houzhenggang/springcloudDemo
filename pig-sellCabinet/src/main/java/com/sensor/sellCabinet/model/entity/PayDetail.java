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
 * 实收明细表
 *
 * @author chengpan
 * @date 2019-05-09 14:27:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_detail")
public class PayDetail extends Model<PayDetail> {
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
   * 货柜编号
   */
    private String cabinetNo;
    /**
   * 通道号
   */
    private Integer channelNo;
    /**
   * 商品编码
   */
    private String productCode;
	/**
	 * 商品名称
	 */
	private String productName;
    /**
   * 分润模式|1=比例|2=定价
   */
    private String rateType;
    /**
   * 费率
   */
    private BigDecimal productPrice;
    /**
   * 金额
   */
    private BigDecimal payAmount;
    /**
   * 创建日期
   */
    private LocalDateTime createDate;
    /**
   * 订单状态（0未付款 1付款成功 2退款中 3已退款）
   */
    private String payStatus;
    /**
   * 开门是否成功（9尚未开柜 失败0/成功1/验证失败2/忙3/参数错误4/状态错误5（电池欠压，从机板正在初始化））
   */
    private String openStatus;

}
