package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensor.sellCabinet.mapper.ProductUserMapper;
import com.sensor.sellCabinet.model.entity.ProductUser;
import com.sensor.sellCabinet.service.ProductUserService;
import org.springframework.stereotype.Service;

/**
 * 商品代理商关系表
 *
 * @author chengpan
 * @date 2019-08-20 17:09:23
 */
@Service("productUserService")
public class ProductUserServiceImpl extends ServiceImpl<ProductUserMapper, ProductUser> implements ProductUserService {

  /**
   * 商品代理商关系表简单分页查询
   * @param productUser 商品代理商关系表
   * @return
   */
  @Override
  public IPage<ProductUser> getProductUserPage(Page<ProductUser> page, ProductUser productUser){
      return baseMapper.getProductUserPage(page,productUser);
  }

	@Override
	public ProductUser getProductUser(ProductUser productUser) {
		return baseMapper.getProductUserPage(productUser);
	}

}
