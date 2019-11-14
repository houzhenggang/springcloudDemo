package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.CountByTeam;
import lombok.Data;

@Data
public class CountByTeamDto extends CountByTeam {
	private String firstAdmin;
	private String secondAdmin;
	private String thirdAdmin;
	private String fourthAdmin;
	private String fifthAdmin;
	private String firstProxyName;
	private String secondProxyName;
	private String thirdProxyName;
	private String fourthProxyName;
	private String fifthProxyName;
}
