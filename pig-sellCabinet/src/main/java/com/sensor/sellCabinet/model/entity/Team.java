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
 * 场地表
 *
 * @author chengpan
 * @date 2019-04-15 10:45:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("team")
public class Team extends Model<Team> {
private static final long serialVersionUID = 1L;

    /**
   * 唯一码
   */
    @TableId
    private Integer id;
    /**
   * 场地编码
   */
    private String teamCode;
    /**
   * 场地名字
   */
    private String teamName;
	/**
	 * 是否自动分润 1是  0不是
	 */
	private String incomeStatus;
    /**
   * 场地描述
   */
    private String teamDescribe;
    /**
   * 设备数量
   */
    private Integer deviceSize;
    /**
   * 平台分润比
   */
    private BigDecimal sensorRate;
    /**
   * 一级代理商
   */
    private String firstAdmin;
    /**
   * 一级代理商分润比
   */
    private BigDecimal firstRate;
    /**
   * 二级代理商
   */
    private String secondAdmin;
    /**
   * 二级代理商分润比
   */
    private BigDecimal secondRate;
    /**
   * 三级代理商
   */
    private String thirdAdmin;
    /**
   * 三级代理商分润比
   */
    private BigDecimal thirdRate;
    /**
   * 四级代理商
   */
    private String fourthAdmin;
    /**
   * 四级代理商分润比
   */
    private BigDecimal fourthRate;
    /**
   * 五级代理商
   */
    private String fifthAdmin;
    /**
   * 五级代理商分润比
   */
    private BigDecimal fifthRate;
	/**
	 * 客服电话
	 */
	private String hotline;
    /**
   * 创建人
   */
    private String createUser;
    /**
   * 创建日期
   */
    private LocalDateTime createDate;
    /**
   * 更新人
   */
    private String updateUser;
    /**
   * 更新时间
   */
    private LocalDateTime updateDate;
  
}
