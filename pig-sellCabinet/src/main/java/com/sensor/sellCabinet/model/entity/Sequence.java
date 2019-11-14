package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 唯一码生成规则表
 *
 * @author chengpan
 * @date 2019-04-17 11:02:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sequence")
public class Sequence extends Model<Sequence> {
private static final long serialVersionUID = 1L;

    /**
   * 表头缩写
   */
    @TableId
    private String name;
    /**
   * 起始值
   */
    private Integer currentValue;
    /**
   * 自增数
   */
    private Integer increment;
  
}
