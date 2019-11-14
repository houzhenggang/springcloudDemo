package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.model.dto.IncomeDto;
import com.sensor.sellCabinet.model.entity.Income;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分润表
 *
 * @author chengpan
 * @date 2019-07-02 10:13:18
 */
public interface IncomeService extends IService<Income> {

  /**
   * 分润表简单分页查询
   * @param income 分润表
   * @return
   */
  IPage<IncomeDto> getIncomePage(Page<IncomeDto> page, IncomeDto income, SysUser user);

	IPage<IncomeDto> getIncomeConfirmPage(Page<IncomeDto> page, IncomeDto income, SysUser user);

	List<IncomeDto> getIncomeList(IncomeDto income);

	List<IncomeDto> confirmIncome(IncomeDto income, SysUser user);
}
