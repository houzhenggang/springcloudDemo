package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.mapper.ProductInfoMapper;
import com.sensor.sellCabinet.model.dto.ProductInfoDto;
import com.sensor.sellCabinet.model.entity.ProductInfo;
import com.sensor.sellCabinet.service.ProductInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品信息表
 *
 * @author chengpan
 * @date 2019-04-15 16:24:22
 */
@Service("productInfoService")
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

  /**
   * 商品信息表简单分页查询
   * @param productInfo 商品信息表
   * @return
   */
  @Override
  public IPage<ProductInfoDto> getProductInfoPage(Page<ProductInfoDto> page, ProductInfoDto productInfo, SysUser user){
      return baseMapper.getProductInfoPage(page,productInfo,user);
  }
	@Override
	public IPage<ProductInfoDto> getProductInfoMobilePage(Page<ProductInfoDto> page, ProductInfoDto productInfo, SysUser user) {
		return baseMapper.getProductInfoMobilePage(page,productInfo,user);
	}

	@Override
	public ProductInfo getProductInfo(String productCode) {
		return baseMapper.getProductInfo(productCode);
	}

	@Override
	public IPage<ProductInfo> getProductByTeamCode(Page page,String teamCode) {
		return baseMapper.getProductByTeamCode(page,teamCode);
	}

}
