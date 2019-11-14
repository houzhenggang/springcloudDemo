package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sensor.sellCabinet.model.entity.Cosumer;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表(消费者)
 *
 * @author chengpan
 * @date 2019-04-10 16:21:00
 */
public interface CosumerService extends IService<Cosumer> {

  /**
   * 用户表(消费者)简单分页查询
   * @param cosumer 用户表(消费者)
   * @return
   */
  IPage<Cosumer> getCosumerPage(Page<Cosumer> page, Cosumer cosumer);


	Cosumer getCosumerByOpenId(String openId);

	Cosumer getCosumerByAliPayUserId(String alipayuserid);

}
