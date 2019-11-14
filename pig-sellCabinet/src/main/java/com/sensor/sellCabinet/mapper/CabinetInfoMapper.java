package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.CabinetInfoDto;
import com.sensor.sellCabinet.model.entity.CabinetInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 货柜信息
 *
 * @author chengpan
 * @date 2019-04-03 11:21:45
 */
public interface CabinetInfoMapper extends BaseMapper<CabinetInfo> {
	/**
	 * 货柜信息简单分页查询
	 * @param cabinetInfo 货柜信息
	 * @return
	 */
	List<CabinetInfoDto> getCabinetInfoPage(Page page, @Param("cabinetInfo") CabinetInfoDto cabinetInfo, @Param("user") SysUser user);

	CabinetInfoDto getCabinetInfoPage( @Param("cabinetInfo") CabinetInfoDto cabinetInfo, @Param("user") SysUser user);

	List<CabinetInfoDto> reportElectric(Page page, @Param("cabinetInfo") CabinetInfoDto cabinetInfo, @Param("user") SysUser user);

	CabinetInfo getCabinet(@Param("cabinetNo") String cabinetNo);

	Boolean updateBattery(@Param("cabinetNo") String cabinetNo, @Param("electric") Integer electric, @Param("reportDate") LocalDateTime reportDate);

	Integer updateCabinetTeamCodeBatch(@Param("list") List<CabinetInfo> cabinetInfoList);

	Integer updetaIsEmpty(@Param("cabinetNo") String cabinetNo);
}
