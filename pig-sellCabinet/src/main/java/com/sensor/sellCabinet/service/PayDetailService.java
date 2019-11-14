package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.PayDetailDto;
import com.sensor.sellCabinet.model.entity.PayDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 实收明细表
 *
 * @author chengpan
 * @date 2019-04-23 16:21:06
 */
public interface PayDetailService extends IService<PayDetail> {

  /**
   * 实收明细表简单分页查询
   * @param payDetail 实收明细表
   * @return
   */
  IPage<PayDetailDto> getPayDetailPage(Page<PayDetail> page, PayDetailDto payDetail, SysUser user);

  IPage<PayDetail> getWechatPayDetailPage(Page<PayDetail> page, PayDetail payDetail);

  int updatePayDetailStatus(String orderCode, String channelNo, String payStatus);

	List<PayDetailDto> getPayDetailForExcel(PayDetailDto payDetail, SysUser user);

	BigDecimal getPayDetailSum(PayDetailDto payDetail, SysUser user);
}
