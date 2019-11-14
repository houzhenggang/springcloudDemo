package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.model.dto.ExtractAsk;
import com.sensor.sellCabinet.model.dto.ExtractCashRecordDto;
import com.sensor.sellCabinet.model.entity.ExtractCashRecord;

/**
 * 提现记录表
 *
 * @author chengpan
 * @date 2019-06-27 21:01:37
 */
public interface ExtractCashRecordService extends IService<ExtractCashRecord> {

  /**
   * 提现记录表简单分页查询
   * @param extractCashRecord 提现记录表
   * @return
   */
  IPage<ExtractCashRecordDto> getExtractCashRecordPage(Page<ExtractCashRecordDto> page, ExtractCashRecordDto extractCashRecord,SysUser user);


	R transfers(SysUser user, ExtractAsk extractAsk);
}
