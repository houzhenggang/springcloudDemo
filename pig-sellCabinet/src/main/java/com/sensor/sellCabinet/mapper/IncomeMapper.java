package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.IncomeDto;
import com.sensor.sellCabinet.model.entity.Income;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分润表
 *
 * @author chengpan
 * @date 2019-07-02 10:13:18
 */
public interface IncomeMapper extends BaseMapper<Income> {
  /**
    * 分润表简单分页查询
    * @param income 分润表
    * @return
    */
  IPage<IncomeDto> getIncomePage(Page page, @Param("income") IncomeDto income, @Param("user") SysUser user);

	List<IncomeDto> getIncomePage(@Param("income") IncomeDto income, @Param("user") SysUser user);

	IPage<IncomeDto> getIncomeConfirmPage(Page page, @Param("income") IncomeDto income, @Param("user") SysUser user);

	List<IncomeDto> getIncomeList(@Param("income") IncomeDto income);

	Income getIncomeByOrderCode(@Param("orderCode") String orderCode);

}
