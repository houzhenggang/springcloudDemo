package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sensor.sellCabinet.model.entity.Battery;
import org.apache.ibatis.annotations.Param;

/**
 * 电池信息表
 *
 * @author chengpan
 * @date 2019-07-22 17:17:41
 */
public interface BatteryMapper extends BaseMapper<Battery> {
  /**
    * 电池信息表简单分页查询
    * @param battery 电池信息表
    * @return
    */
  IPage<Battery> getBatteryPage(Page page, @Param("battery") Battery battery);


	Battery getBatteryByElectric(@Param("battery") Battery battery);
}
