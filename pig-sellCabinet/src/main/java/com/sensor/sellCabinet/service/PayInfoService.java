package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.PayInfoDto;
import com.sensor.sellCabinet.model.dto.PayInfoV;
import com.sensor.sellCabinet.model.dto.SaleCount;
import com.sensor.sellCabinet.model.entity.CountByTeam;
import com.sensor.sellCabinet.model.entity.Income;
import com.sensor.sellCabinet.model.entity.PayInfo;
import com.sensor.sellCabinet.model.entity.Refund;
import com.sensor.sellCabinet.util.JsonMap;


import java.util.List;
import java.util.Map;

/**
 * 实收汇总表
 *
 * @author chengpan
 * @date 2019-04-23 16:20:54
 */
public interface PayInfoService extends IService<PayInfo>{

  /**
   * 实收汇总表简单分页查询
   * @param payInfo 实收汇总表
   * @return
   */
  IPage<PayInfoV> getPayInfoPage(Page<PayInfo> page, PayInfoV payInfo);


	List<PayInfoV> getPayInfoListForExcel(PayInfoV payInfo);

	Boolean savePayInfoDto(PayInfoDto payInfoDto);

	PayInfo getPayInfoByOrderCode(String ordercode);

	List<PayInfo> getWechatPayInfoList(PayInfo payInfo);

	Boolean savePayInfoDtoByAli(PayInfoDto payInfoDto);

	JsonMap pay(PayInfoDto payInfoDto,String userId,String openId) throws Exception;

	JsonMap payByAliPay(PayInfoDto payInfoDto,String userId,String openId) throws Exception;

	String receiveSgin(String param) throws Exception;

	String receiveSginByAli(Map<String, String> param) throws Exception;

	Boolean addRefund(Refund refund) throws Exception;

	Boolean refund(Refund refund);

	Boolean refundByAli(Refund refund) throws Exception;

	Boolean openCabinet(String cabinetNo,String orderCode,Integer channelNo,String openStatus);

	SaleCount getSaleCount(PayInfoV payInfoV, SysUser user);

	Boolean updateBalance(Income income);

	IPage<CountByTeam> getCountByTeam(Page page, CountByTeam countByTeam, SysUser user);

	IPage<CountByTeam> getCountByCabinet(Page page, CountByTeam countByTeam, SysUser user);

	IPage<CountByTeam> getCountByProxy(Page page, CountByTeam countByTeam, SysUser user);
}
