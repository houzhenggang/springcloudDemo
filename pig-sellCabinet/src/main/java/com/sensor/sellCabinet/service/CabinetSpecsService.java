package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sensor.sellCabinet.model.entity.CabinetSpecs;

import java.util.List;

/**
 * 售货柜规格表
 *
 * @author chengpan
 * @date 2019-05-07 09:31:50
 */
public interface CabinetSpecsService extends IService<CabinetSpecs> {

  /**
   * 售货柜规格表简单分页查询
   * @param cabinetSpecs 售货柜规格表
   * @return
   */
  IPage<CabinetSpecs> getCabinetSpecsPage(Page<CabinetSpecs> page, CabinetSpecs cabinetSpecs);


	List<CabinetSpecs> getCabinetSpecsByNo(String specsNo);
}
