package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sensor.sellCabinet.model.entity.ProductUser;
import org.apache.ibatis.annotations.Param;

/**
 * 商品代理商关系表
 *
 * @author chengpan
 * @date 2019-08-20 17:09:23
 */
public interface ProductUserMapper extends BaseMapper<ProductUser> {
  /**
    * 商品代理商关系表简单分页查询
    * @param productUser 商品代理商关系表
    * @return
    */
  IPage<ProductUser> getProductUserPage(Page page, @Param("productUser") ProductUser productUser);

	ProductUser getProductUserPage(@Param("productUser") ProductUser productUser);
}
