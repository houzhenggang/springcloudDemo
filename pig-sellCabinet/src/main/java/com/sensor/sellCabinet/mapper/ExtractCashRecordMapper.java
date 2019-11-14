package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.ExtractCashRecordDto;
import com.sensor.sellCabinet.model.entity.ExtractCashRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 提现记录表
 *
 * @author chengpan
 * @date 2019-06-27 21:01:37
 */
public interface ExtractCashRecordMapper extends BaseMapper<ExtractCashRecord> {
  /**
    * 提现记录表简单分页查询
    * @param extractCashRecord 提现记录表
    * @return
    */
  IPage<ExtractCashRecordDto> getExtractCashRecordPage(Page page, @Param("extractCashRecord") ExtractCashRecordDto extractCashRecord, @Param("user") SysUser user);

	ExtractCashRecord getExtractCashRecordByTranNo(@Param("transNo") String recordId);
}
