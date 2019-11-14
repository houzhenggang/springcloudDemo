package com.sensor.sellCabinet.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.sensor.sellCabinet.mapper.BatteryMapper;
import com.sensor.sellCabinet.mapper.CabinetInfoMapper;
import com.sensor.sellCabinet.mapper.UserMapper;
import com.sensor.sellCabinet.model.dto.CabinetInfoDto;
import com.sensor.sellCabinet.model.entity.Battery;
import com.sensor.sellCabinet.model.entity.CabinetInfo;
import com.sensor.sellCabinet.service.CabinetInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 货柜信息
 *
 * @author chengpan
 * @date 2019-04-03 11:21:45
 */
@Service("cabinetInfoService")
@AllArgsConstructor
public class CabinetInfoServiceImpl extends ServiceImpl<CabinetInfoMapper, CabinetInfo> implements CabinetInfoService {

	private UserMapper userMapper;

	private BatteryMapper batteryMapper;

	/**
	 * 货柜信息简单分页查询
	 * @param cabinetInfo 货柜信息
	 * @return
	 */
	@Override
	public IPage<CabinetInfoDto> getCabinetInfoPage(Page<CabinetInfoDto> page, CabinetInfoDto cabinetInfo){
		Integer userId = SecurityUtils.getUser().getId();
		SysUser user = userMapper.getSysUser(userId);
		List<CabinetInfoDto> cabinetList = baseMapper.getCabinetInfoPage(page,cabinetInfo,user);
		return page.setRecords(cabinetList);
	}

	@Override
	public IPage<CabinetInfoDto> reportElectric(Page<CabinetInfoDto> page, CabinetInfoDto cabinetInfo) {
		Integer userId = SecurityUtils.getUser().getId();
		SysUser user = userMapper.getSysUser(userId);
		List<CabinetInfoDto> cabinetList = baseMapper.reportElectric(page,cabinetInfo,user);
		return page.setRecords(cabinetList);
	}

	@Override
	public CabinetInfo getCabinet(String cabinetNo) {
		return baseMapper.getCabinet(cabinetNo);
	}

	@Override
	public Boolean updateBattery(String cabinetNo, Integer electric,String specs) {
		Battery battery = new Battery();
		battery.setElectric(electric);
		battery.setSpecs(specs);
		Battery rebattery = batteryMapper.getBatteryByElectric(battery);
		if (null!=rebattery){
			int days = rebattery.getDays();
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime reportDate =  now.plusDays(days);
			return baseMapper.updateBattery(cabinetNo,electric,reportDate);
		}else {
			return false;
		}
	}

	@Override
	public Integer updateCabinetTeamCodeBatch(List<CabinetInfo> cabinetInfoList) {
		return baseMapper.updateCabinetTeamCodeBatch(cabinetInfoList);
	}

	@Override
	public CabinetInfo getCabinetByUser(String cabinetNo) {
		Integer userId = SecurityUtils.getUser().getId();
		SysUser user = userMapper.getSysUser(userId);
		CabinetInfoDto cabinetInfo = new CabinetInfoDto();
		cabinetInfo.setCabinetNo(cabinetNo);
		CabinetInfoDto res= baseMapper.getCabinetInfoPage(cabinetInfo,user);
		return res;
	}

	@Override
	public Integer updetaIsEmpty(String cabinetNo) {
		return baseMapper.updetaIsEmpty(cabinetNo);
	}
}
