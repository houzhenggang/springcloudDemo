package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sensor.sellCabinet.model.entity.CabinetSpecs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 售货柜规格表
 *
 * @author chengpan
 * @date 2019-05-07 09:31:50
 */
public interface CabinetSpecsMapper extends BaseMapper<CabinetSpecs> {
  /**
    * 售货柜规格表简单分页查询
    * @param cabinetSpecs 售货柜规格表
    * @return
    */
  IPage<CabinetSpecs> getCabinetSpecsPage(Page page, @Param("cabinetSpecs") CabinetSpecs cabinetSpecs);

	List<CabinetSpecs> getCabinetSpecsByNo(@Param("specsNo") String specsNo);
}
