package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public interface PayDetailMapper extends BaseMapper<PayDetail> {
  /**
    * 实收明细表简单分页查询
    * @param payDetail 实收明细表
    * @return
    */
  IPage<PayDetailDto> getPayDetailPage(Page page, @Param("payDetail") PayDetailDto payDetail, @Param("user")SysUser user);

	List<PayDetailDto> getPayDetailPage(@Param("payDetail") PayDetailDto payDetail, @Param("user")SysUser user);

  IPage<PayDetail> getWechatPayDetailPage(Page page, @Param("payDetail") PayDetail payDetail);

	int updatePayDetailStatus(@Param("orderCode") String orderCode,@Param("channelNo") String channelNo,@Param("payStatus") String payStatus);

	int openCabinet(@Param("orderCode") String orderCode,@Param("channelNo") Integer channelNo,@Param("openStatus") String openStatus);

	BigDecimal getPayDetailSum(@Param("payDetail") PayDetailDto payDetail, @Param("user")SysUser user);
}
