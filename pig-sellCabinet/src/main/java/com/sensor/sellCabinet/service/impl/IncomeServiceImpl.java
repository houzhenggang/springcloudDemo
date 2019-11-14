package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.mapper.IncomeMapper;
import com.sensor.sellCabinet.mapper.PayInfoMapper;
import com.sensor.sellCabinet.model.dto.IncomeDto;
import com.sensor.sellCabinet.model.entity.Income;
import com.sensor.sellCabinet.service.IncomeService;
import com.sensor.sellCabinet.util.DateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分润表
 *
 * @author chengpan
 * @date 2019-07-02 10:13:18
 */
@Service("incomeService")
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income> implements IncomeService {

  /**
   * 分润表简单分页查询
   * @param income 分润表
   * @return
   */
  @Override
  public IPage<IncomeDto> getIncomePage(Page<IncomeDto> page, IncomeDto income, SysUser user){
  	if(income.getEDate()!=null&&!"".equals(income.getEDate())){
		income.setEDate(DateUtil.getTheLastTime(income.getEDate()));
	}
      return baseMapper.getIncomePage(page,income,user);
  }

	@Override
	public IPage<IncomeDto> getIncomeConfirmPage(Page<IncomeDto> page, IncomeDto income, SysUser user) {
		return baseMapper.getIncomeConfirmPage(page,income,user);
	}

	@Override
	public List<IncomeDto> getIncomeList(IncomeDto income) {
		return baseMapper.getIncomeList(income);
	}

	@Override
	public List<IncomeDto> confirmIncome(IncomeDto income, SysUser user) {
		return baseMapper.getIncomePage(income, user);
	}
}
