package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.RunCabinetProductInfo;
import com.sensor.sellCabinet.model.entity.RunCabinetProduct;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运行货柜商品信息
 *
 * @author chengpan
 * @date 2019-04-15 17:36:58
 */
public interface RunCabinetProductService extends IService<RunCabinetProduct> {

  /**
   * 运行货柜商品信息简单分页查询
   * @param runCabinetProduct 运行货柜商品信息
   * @return
   */
  IPage<RunCabinetProductInfo> getRunCabinetProductPage(Page<RunCabinetProduct> page, RunCabinetProduct runCabinetProduct,SysUser user);

	List<RunCabinetProductInfo> getRunCabinetProductDtoList(String cabinetNo,SysUser user);

	List<RunCabinetProductInfo> getWeRunCabinetProductDtoList(String cabinetNo);

	int updateProductPrice(String productCode, String currentPrice,String teamCode);

	Boolean updateRunCabinet(String cabinetNo, Integer currentNum,LocalDateTime effictSdate,LocalDateTime effictEdate,LocalDateTime updateTime);

	void updateProduct(RunCabinetProduct runCabinetProduct);

	RunCabinetProduct getRunCabinetProduct(String cabinetNo,Integer channelNo);
}
