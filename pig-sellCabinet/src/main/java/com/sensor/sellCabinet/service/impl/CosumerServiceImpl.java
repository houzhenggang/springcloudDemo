package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensor.sellCabinet.mapper.CosumerMapper;
import com.sensor.sellCabinet.model.entity.Cosumer;
import com.sensor.sellCabinet.service.CosumerService;
import org.springframework.stereotype.Service;

/**
 * 用户表(消费者)
 *
 * @author chengpan
 * @date 2019-04-10 16:21:00
 */
@Service("cosumerService")
public class CosumerServiceImpl extends ServiceImpl<CosumerMapper, Cosumer> implements CosumerService {

  /**
   * 用户表(消费者)简单分页查询
   * @param cosumer 用户表(消费者)
   * @return
   */
  @Override
  public IPage<Cosumer> getCosumerPage(Page<Cosumer> page, Cosumer cosumer){
      return baseMapper.getCosumerPage(page,cosumer);
  }

	@Override
	public Cosumer getCosumerByOpenId(String openId) {
		return baseMapper.getCosumerByOpenId(openId);
	}

	@Override
	public Cosumer getCosumerByAliPayUserId(String alipayuserid) {
		return baseMapper.getCosumerByAliPayUserId(alipayuserid);
	}
}
