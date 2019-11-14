package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 运行货柜商品信息
 *
 * @author chengpan
 * @date 2019-04-15 17:36:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("run_cabinet_product")
public class RunCabinetProduct extends Model<RunCabinetProduct> {
private static final long serialVersionUID = 1L;

    /**
   * 唯一键
   */
    @TableId
    private Integer id;
    /**
   * 货柜编号
   */
    private String cabinetNo;
    /**
   * 通道号
   */
    private Integer channelNo;
    /**
   * 商品编号
   */
    private String productCode;
    /**
   * 当前数量
   */
    private Integer currentNum;
	/**
	 * 货道最大数量
	 */
    private Integer maxNum;
    /**
   * 当前价格
   */
    private BigDecimal currentPrice;
	/**
	 * 更新时间（补货时间）
	 */
	private LocalDateTime updateTime;
    /**
   * 价格有效期开始
   */
    private LocalDateTime effictSdate;
    /**
   * 价格有效期结束
   */
    private LocalDateTime effictEdate;
    /**
   * 临时价格
   */
    private BigDecimal temporaryprice;
	/**
	 * 商品状态(0=未确认|1=已确认
	 */
	private String productStatus;
  
}
