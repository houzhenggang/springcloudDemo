package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sensor.sellCabinet.model.entity.ProductUser;

/**
 * 商品代理商关系表
 *
 * @author chengpan
 * @date 2019-08-20 17:09:23
 */
public interface ProductUserService extends IService<ProductUser> {

  /**
   * 商品代理商关系表简单分页查询
   * @param productUser 商品代理商关系表
   * @return
   */
  IPage<ProductUser> getProductUserPage(Page<ProductUser> page, ProductUser productUser);


	ProductUser getProductUser(ProductUser productUser);
}
