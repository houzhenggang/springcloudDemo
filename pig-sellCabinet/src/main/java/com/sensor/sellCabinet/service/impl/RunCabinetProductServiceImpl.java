package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.mapper.RunCabinetProductMapper;
import com.sensor.sellCabinet.model.dto.RunCabinetProductInfo;
import com.sensor.sellCabinet.model.entity.RunCabinetProduct;
import com.sensor.sellCabinet.service.RunCabinetProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运行货柜商品信息
 *
 * @author chengpan
 * @date 2019-04-15 17:36:58
 */
@Service("runCabinetProductService")
public class RunCabinetProductServiceImpl extends ServiceImpl<RunCabinetProductMapper, RunCabinetProduct> implements RunCabinetProductService {

  /**
   * 运行货柜商品信息简单分页查询
   * @param runCabinetProduct 运行货柜商品信息
   * @return
   */
  @Override
  public IPage<RunCabinetProductInfo> getRunCabinetProductPage(Page<RunCabinetProduct> page, RunCabinetProduct runCabinetProduct,SysUser user){
      return baseMapper.getRunCabinetProductPage(page,runCabinetProduct,user);
  }

	@Override
	public List<RunCabinetProductInfo> getRunCabinetProductDtoList(String cabinetNo,SysUser user) {
		return baseMapper.getRunCabinetProductDtoList(cabinetNo,user);
	}

	@Override
	public List<RunCabinetProductInfo> getWeRunCabinetProductDtoList(String cabinetNo) {
		return baseMapper.getWeRunCabinetProductDtoList(cabinetNo);
	}

	@Override
	public int updateProductPrice(String productCode, String currentPrice,String teamCode) {
		return baseMapper.updateProductPrice(productCode,currentPrice,teamCode);
	}

	@Override
	public Boolean updateRunCabinet(String cabinetNo,Integer currentNum, LocalDateTime effictSdate, LocalDateTime effictEdate,LocalDateTime updateTime) {
		return baseMapper.updateRunCabinet(cabinetNo,currentNum,effictSdate,effictEdate,updateTime);
	}

	@Override
	public void updateProduct(RunCabinetProduct runCabinetProduct) {
		baseMapper.updateProduct(runCabinetProduct);
	}

	@Override
	public RunCabinetProduct getRunCabinetProduct(String cabinetNo, Integer channelNo) {
		return baseMapper.getRunCabinetProduct(cabinetNo,channelNo);
	}

}
