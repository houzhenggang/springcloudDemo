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
 * 分润表
 *
 * @author chengpan
 * @date 2019-07-02 10:13:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("income")
public class Income extends Model<Income> {
private static final long serialVersionUID = 1L;

    /**
   * 主键id
   */
    @TableId
    private Integer id;
    /**
   * 实收总账编号
   */
    private String orderCode;
    /**
   * 场地编码
   */
    private String teamCode;
    /**
   * 总分润金额
   */
    private BigDecimal totalAmount;
    /**
   * 平台分润金额
   */
    private BigDecimal sensorIncome;
    /**
   * 一级代理商
   */
    private String firstAdmin;
    /**
   * 分润金额
   */
    private BigDecimal firstIncome;
    /**
   * 二级代理商
   */
    private String secondAdmin;
    /**
   * 分润金额
   */
    private BigDecimal secondIncome;
    /**
   * 三级代理商
   */
    private String thirdAdmin;
    /**
   * 分润金额
   */
    private BigDecimal thirdIncome;
    /**
   * 四级代理商
   */
    private String fourthAdmin;
    /**
   * 分润金额
   */
    private BigDecimal fourthIncome;
    /**
   * 五级代理商
   */
    private String fifthAdmin;
    /**
   * 分润金额
   */
    private BigDecimal fifthIncome;
    /**
   * 创建日期
   */
    private LocalDateTime createDate;
    /**
   * 
   */
    private String incomeFlag;
    /**
   * 分润状态
   */
    private String status;
  
}
