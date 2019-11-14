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
 * 售货柜规格表
 *
 * @author chengpan
 * @date 2019-05-07 09:31:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cabinet_specs")
public class CabinetSpecs extends Model<CabinetSpecs> {
private static final long serialVersionUID = 1L;

    /**
   * 唯一码
   */
    @TableId
    private Integer id;
    /**
   * 规格编号
   */
    private String specsNo;
    /**
   * 行
   */
    private Integer channelLine;
    /**
   * 列
   */
    private Integer channelRow;
  
}
