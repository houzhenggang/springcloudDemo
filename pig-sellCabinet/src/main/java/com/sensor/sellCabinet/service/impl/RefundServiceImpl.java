package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.sensor.sellCabinet.mapper.RefundMapper;
import com.sensor.sellCabinet.mapper.UserMapper;
import com.sensor.sellCabinet.model.dto.RefundDto;
import com.sensor.sellCabinet.model.entity.Income;
import com.sensor.sellCabinet.model.entity.Refund;
import com.sensor.sellCabinet.service.RefundService;
import com.sensor.sellCabinet.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 退款信息表
 *
 * @author chengpan
 * @date 2019-05-15 12:15:32
 */
@Service("refundService")
@AllArgsConstructor
public class RefundServiceImpl extends ServiceImpl<RefundMapper, Refund> implements RefundService {

	private UserMapper userMapper;

  /**
   * 退款信息表简单分页查询
   * @param refund 退款信息表
   * @return
   */
  @Override
  public IPage<RefundDto> getRefundPage(Page<Refund> page, RefundDto refund){
	  Integer userId = SecurityUtils.getUser().getId();
	  SysUser user = userMapper.getSysUser(userId);
	  if (refund.getEDate()!=null&&!"".equals(refund.getEDate())){
		  refund.setEDate(DateUtil.getTheLastTime(refund.getEDate()));
	  }
      return baseMapper.getRefundPage(page,refund,user);
  }

	@Override
	public IPage<RefundDto> getRefundList(Page<Refund> page) {
		Integer userId = SecurityUtils.getUser().getId();
		SysUser user = userMapper.getSysUser(userId);
		RefundDto refund = new RefundDto();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime minusDay = localDateTime.minusDays(1);
		refund.setSDate(df.format(minusDay));
		refund.setRefundStatus("0");
		return baseMapper.getRefundList(page,refund,user);
	}

}
