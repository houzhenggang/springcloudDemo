package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensor.sellCabinet.mapper.CabinetSpecsMapper;
import com.sensor.sellCabinet.model.entity.CabinetSpecs;
import com.sensor.sellCabinet.service.CabinetSpecsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 售货柜规格表
 *
 * @author chengpan
 * @date 2019-05-07 09:31:50
 */
@Service("cabinetSpecsService")
public class CabinetSpecsServiceImpl extends ServiceImpl<CabinetSpecsMapper, CabinetSpecs> implements CabinetSpecsService {

  /**
   * 售货柜规格表简单分页查询
   * @param cabinetSpecs 售货柜规格表
   * @return
   */
  @Override
  public IPage<CabinetSpecs> getCabinetSpecsPage(Page<CabinetSpecs> page, CabinetSpecs cabinetSpecs){
      return baseMapper.getCabinetSpecsPage(page,cabinetSpecs);
  }

	@Override
	public List<CabinetSpecs> getCabinetSpecsByNo(String specsNo) {
		return baseMapper.getCabinetSpecsByNo(specsNo);
	}

}
