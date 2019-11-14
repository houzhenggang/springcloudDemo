package com.sensor.sellCabinet.util;

import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.model.dto.IncomeDto;
import com.sensor.sellCabinet.model.dto.TeamDto;
import com.sensor.sellCabinet.model.entity.Income;
import com.sensor.sellCabinet.model.entity.Team;
import com.sensor.sellCabinet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MapperUtil {

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private IncomeService incomeService;
	@Autowired
	private PayInfoService payInfoService;
	@Autowired
	private CabinetInfoService cabinetInfoService;
	@Autowired
	private TeamService teamService;

	public static MapperUtil mapperUtil;

	@PostConstruct
	public void init() {
		mapperUtil = this;
		mapperUtil.userInfoService = this.userInfoService;
		mapperUtil.incomeService = this.incomeService;
		mapperUtil.payInfoService = this.payInfoService;
		mapperUtil.teamService = this.teamService;
		mapperUtil.cabinetInfoService = this.cabinetInfoService;
	}

	public static void updateBalance(){
		IncomeDto income = new IncomeDto();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime minusDay = localDateTime.minusDays(1);
		income.setEDate(df.format(minusDay));
		income.setIncomeStatus("1");
		List<IncomeDto> IncomeList = mapperUtil.incomeService.getIncomeList(income);
		for (Income inco:IncomeList) {
			mapperUtil.payInfoService.updateBalance(inco);
		}
	}
}




