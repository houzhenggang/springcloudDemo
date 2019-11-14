package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.ProductInfoDto;
import com.sensor.sellCabinet.model.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品信息表
 *
 * @author chengpan
 * @date 2019-04-15 16:24:22
 */
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
  /**
    * 商品信息表简单分页查询
    * @param productInfo 商品信息表
    * @return
    */
  IPage<ProductInfoDto> getProductInfoPage(Page page, @Param("productInfo") ProductInfoDto productInfo, @Param("user") SysUser user);

  IPage<ProductInfoDto> getProductInfoMobilePage(Page page, @Param("productInfo") ProductInfoDto productInfo, @Param("user") SysUser user);

	ProductInfo getProductInfo(@Param("productCode") String productCode);

	IPage<ProductInfo> getProductByTeamCode(Page page,@Param("teamCode") String teamCode);

}
