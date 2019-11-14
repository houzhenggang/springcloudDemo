package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.mapper.PayDetailMapper;
import com.sensor.sellCabinet.model.dto.PayDetailDto;
import com.sensor.sellCabinet.model.entity.PayDetail;
import com.sensor.sellCabinet.service.PayDetailService;
import com.sensor.sellCabinet.util.DateUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 实收明细表
 *
 * @author chengpan
 * @date 2019-04-23 16:21:06
 */
@Service("payDetailService")
public class PayDetailServiceImpl extends ServiceImpl<PayDetailMapper, PayDetail> implements PayDetailService {

  /**
   * 实收明细表简单分页查询
   * @param payDetail 实收明细表
   * @return
   */
  @Override
  public IPage<PayDetailDto> getPayDetailPage(Page<PayDetail> page, PayDetailDto payDetail, SysUser user){
  	if (payDetail.getEDate()!=null&&!"".equals(payDetail.getEDate())){
		payDetail.setEDate(DateUtil.getTheLastTime(payDetail.getEDate()));
	}
      return baseMapper.getPayDetailPage(page,payDetail,user);
  }

  @Override
  public IPage<PayDetail> getWechatPayDetailPage(Page<PayDetail> page, PayDetail payDetail){
      return baseMapper.getWechatPayDetailPage(page,payDetail);
  }

	@Override
	public int updatePayDetailStatus(String orderCode, String channelNo, String payStatus) {
		return baseMapper.updatePayDetailStatus(orderCode,channelNo,payStatus);
	}

	@Override
	public List<PayDetailDto> getPayDetailForExcel(PayDetailDto payDetail, SysUser user) {
		return baseMapper.getPayDetailPage(payDetail,user);
	}

	@Override
	public BigDecimal getPayDetailSum(PayDetailDto payDetail, SysUser user) {
		if (payDetail.getEDate()!=null&&!"".equals(payDetail.getEDate())){
			payDetail.setEDate(DateUtil.getTheLastTime(payDetail.getEDate()));
		}
		return baseMapper.getPayDetailSum(payDetail,user);
	}

}
