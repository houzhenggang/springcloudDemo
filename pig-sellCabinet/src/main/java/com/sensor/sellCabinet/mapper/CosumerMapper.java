package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sensor.sellCabinet.model.entity.Cosumer;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表(消费者)
 *
 * @author chengpan
 * @date 2019-04-10 16:21:00
 */
public interface CosumerMapper extends BaseMapper<Cosumer> {
  /**
    * 用户表(消费者)简单分页查询
    * @param cosumer 用户表(消费者)
    * @return
    */
  IPage<Cosumer> getCosumerPage(Page page, @Param("cosumer") Cosumer cosumer);

	Cosumer getCosumerByOpenId(@Param("openId")String openId);

	Cosumer getCosumerByAliPayUserId(@Param("alipayuserid")String alipayuserid);
}
