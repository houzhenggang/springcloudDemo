package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sensor.sellCabinet.model.dto.RefundDto;
import com.sensor.sellCabinet.model.entity.Refund;

import java.util.List;

/**
 * 退款信息表
 *
 * @author chengpan
 * @date 2019-05-15 12:15:32
 */
public interface RefundService extends IService<Refund> {

  /**
   * 退款信息表简单分页查询
   * @param refund 退款信息表
   * @return
   */
  IPage<RefundDto> getRefundPage(Page<Refund> page, RefundDto refund);


	IPage<RefundDto> getRefundList(Page<Refund> page);
}
