package com.sensor.sellCabinet.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

/**
 * 用户表(消费者)
 *
 * @author chengpan
 * @date 2019-04-15 10:46:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("cosumer")
public class Cosumer extends Model<Cosumer> {
private static final long serialVersionUID = 1L;

    /**
   * 唯一码
   */
    @TableId
    private Integer id;
    /**
   * 用户id
   */
    private String userId;
    /**
   * 用户名字
   */
    private String userName;
    /**
   * 用户电话
   */
    private String mobile;
    /**
   * 用户微信唯一标识
   */
    private String openId;
    /**
   * 用户性别
   */
    private String gender;
    /**
   * 所在城市
   */
    private String city;
    /**
   * 所在省份
   */
    private String province;
    /**
   * 国籍
   */
    private String country;
    /**
   *
   */
    private Integer balance;
    /**
   *
   */
    private String avatarUrl;
    /**
   *
   */
    private LocalDateTime createDate;

	/**
	 * 支付宝用户id
	 */
	private String alipayuserid;
}
