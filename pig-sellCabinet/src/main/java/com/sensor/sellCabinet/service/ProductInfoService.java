package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.ProductInfoDto;
import com.sensor.sellCabinet.model.entity.ProductInfo;

import java.util.List;

/**
 * 商品信息表
 *
 * @author chengpan
 * @date 2019-04-15 16:24:22
 */
public interface ProductInfoService extends IService<ProductInfo> {

  /**
   * 商品信息表简单分页查询
   * @param productInfo 商品信息表
   * @return
   */
  IPage<ProductInfoDto> getProductInfoPage(Page<ProductInfoDto> page, ProductInfoDto productInfo, SysUser user);

	ProductInfo getProductInfo(String productCode);

	IPage<ProductInfo> getProductByTeamCode(Page page,String teamCode);

	IPage<ProductInfoDto> getProductInfoMobilePage(Page<ProductInfoDto> page, ProductInfoDto productInfo, SysUser user);
}
