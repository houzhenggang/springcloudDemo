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
 * 电池信息表
 *
 * @author chengpan
 * @date 2019-07-22 17:17:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("battery")
public class Battery extends Model<Battery> {
private static final long serialVersionUID = 1L;

    /**
   * 主键id
   */
    @TableId
    private Integer id;
    /**
   * 规格型号
   */
    private String specs;
    /**
   * 电量
   */
    private Integer electric;
    /**
   * 电量百分比
   */
    private BigDecimal voltage;
    /**
   * 剩余天数
   */
    private Integer days;
  
}
