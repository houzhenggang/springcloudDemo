package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.PayInfoV;
import com.sensor.sellCabinet.model.entity.CountByTeam;
import com.sensor.sellCabinet.model.entity.PayInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 实收汇总表
 *
 * @author chengpan
 * @date 2019-04-23 16:20:54
 */
public interface PayInfoMapper extends BaseMapper<PayInfo> {
  /**
    * 实收汇总表简单分页查询
    * @param payInfo 实收汇总表
    * @return
    */
    IPage<PayInfoV> getPayInfoPage(Page page, @Param("payInfo") PayInfoV payInfo,@Param("user") SysUser user);

	List<PayInfoV> getPayInfoPage(@Param("payInfo")PayInfoV payInfo,@Param("user") SysUser user);

	List<PayInfo> getWechatPayInfoList(@Param("payInfo")PayInfo payInfo);

	PayInfo getPayInfo(@Param("orderCode")String orderCode);

	int updatePayStatus(@Param("orderCode")String orderCode, @Param("totalFee")String totalFee);

	BigDecimal getSaleCount(@Param("payInfoParam") PayInfoV payInfoV, @Param("user")SysUser user);

	int getBillCount(@Param("payInfoParam") PayInfoV payInfoV, @Param("user")SysUser user);

	IPage<CountByTeam>getCountByTeam(Page page, @Param("countByTeam") CountByTeam countByTeam, @Param("user") SysUser user);

	IPage<CountByTeam> getCountByCabinet(Page page, @Param("countByTeam") CountByTeam countByTeam, @Param("user") SysUser user);

	IPage<CountByTeam> getCountByProxy(Page page, @Param("countByTeam") CountByTeam countByTeam, @Param("user") SysUser user);

	BigDecimal getExtracted(@Param("user") SysUser user);

	BigDecimal getNoExtracted(@Param("user") SysUser user);
}
