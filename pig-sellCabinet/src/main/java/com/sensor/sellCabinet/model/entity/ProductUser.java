package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品代理商关系表
 *
 * @author chengpan
 * @date 2019-08-20 17:09:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_user")
public class ProductUser extends Model<ProductUser> {
private static final long serialVersionUID = 1L;

    /**
   * 主键id
   */
    @TableId
    private Integer id;
    /**
   * 代理商id
   */
    private String userId;
    /**
   * 商品编码
   */
    private String productCode;
  
}
