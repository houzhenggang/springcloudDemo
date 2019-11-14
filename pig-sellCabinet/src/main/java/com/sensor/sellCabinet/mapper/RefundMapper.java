package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.RefundDto;
import com.sensor.sellCabinet.model.entity.Refund;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 退款信息表
 *
 * @author chengpan
 * @date 2019-05-15 12:15:32
 */
public interface RefundMapper extends BaseMapper<Refund> {
  /**
    * 退款信息表简单分页查询
    * @param refund 退款信息表
    * @return
    */
  IPage<RefundDto> getRefundPage(Page page, @Param("refund") RefundDto refund, @Param("user")SysUser user);

	List<Refund> getRefund(@Param("refund") Refund refund);

	int updateRefundStatus(@Param("refundId") String refundId, @Param("refundStatus")String refundStatus);

	IPage<RefundDto> getRefundList(Page page,@Param("refund") RefundDto refund, @Param("user")SysUser user);
}
