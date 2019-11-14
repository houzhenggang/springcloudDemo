package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品信息表
 *
 * @author chengpan
 * @date 2019-04-15 16:24:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("product_info")
public class ProductInfo extends Model<ProductInfo> {
private static final long serialVersionUID = 1L;

    /**
   * 唯一键
   */
    @TableId
    private Integer id;
    /**
   * 商品编号
   */
    private String productCode;
    /**
   * 商品名称
   */
    private String productName;
    /**
   * (多张照片存储问题)
   */
    private String productImages;
    /**
   * 货柜代理商
   */
    private String proxyBusiness;
    /**
   * 库存
   */
    private BigDecimal stock;
    /**
   * 零售价上限
   */
    private BigDecimal upperLimit;
    /**
   * 零售价下限
   */
    private BigDecimal lowerLimit;
    /**
   * 商品入库人
   */
    private String operator;
    /**
   * 当前售价
   */
    private BigDecimal sellPrice;
    /**
   * 成本价
   */
    private BigDecimal costPrice;
    /**
   * 最后操作时间
   */
    private LocalDateTime lastModifydate;
    /**
   * 预警库存
   */
    private BigDecimal alarmStock;
    /**
   * 商品别名
   */
    private String productAlias;
    /**
   * 入库时间
   */
    private LocalDateTime importDate;
    /**
   * 确认时间
   */
    private String confirmDate;
    /**
   * 有效状态 1有效  0无效
   */
    private String effectStatus;
	/**
   * 商品详情编号
   */
    private String detailId;
  
}
