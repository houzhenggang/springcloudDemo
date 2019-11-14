package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sensor.sellCabinet.model.dto.CabinetInfoDto;
import com.sensor.sellCabinet.model.entity.CabinetInfo;

import java.util.List;

/**
 * 货柜信息
 *
 * @author chengpan
 * @date 2019-04-03 11:21:45
 */
public interface CabinetInfoService extends IService<CabinetInfo> {

	/**
	 * 货柜信息简单分页查询
	 * @param cabinetInfo 货柜信息
	 * @return
	 */
	IPage<CabinetInfoDto> getCabinetInfoPage(Page<CabinetInfoDto> page, CabinetInfoDto cabinetInfo);

	IPage<CabinetInfoDto> reportElectric(Page<CabinetInfoDto> page, CabinetInfoDto cabinetInfo);

	CabinetInfo getCabinet(String cabinetNo);

	Boolean updateBattery(String cabinetNo, Integer electric,String specs);

	Integer updateCabinetTeamCodeBatch(List<CabinetInfo> cabinetInfoList);

	CabinetInfo getCabinetByUser(String cabinetNo);

	Integer updetaIsEmpty(String cabinetNo);
}
