package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提现记录表
 *
 * @author chengpan
 * @date 2019-06-27 21:01:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("extract_cash_record")
public class ExtractCashRecord extends Model<ExtractCashRecord> {
private static final long serialVersionUID = 1L;

    /**
   * 
   */
    @TableId
    private String id;
    /**
   * 商户订单号
   */
    private String orgNo;
    /**
   * 第三方交易单号
   */
    private String transNo;
    /**
   * 第三方唯一标识
   */
    private String openid;
    /**
   * 提取人
   */
    private String extractPer;
    /**
   * 提取时间
   */
    private LocalDateTime extractDate;
    /**
   * 提取状态|0=已申请|1=提取成功|2=提取失败
   */
    private String extractStatus;
    /**
   * 提取金额
   */
    private BigDecimal extractMoney;
    /**
   * 到账时间
   */
    private LocalDateTime arriveDate;
    /**
   * 提取备注
   */
    private String extractRemark;
    /**
   * 提取失败原因
   */
    private String extractFailReason;
    /**
   * 提取前金额
   */
    private BigDecimal extractBeforeBalance;
    /**
   * 提取后金额
   */
    private BigDecimal extractAfterBalance;
    /**
   * 提取方式|1=微信|2=支付宝
   */
    private String extractWay;
  
}
