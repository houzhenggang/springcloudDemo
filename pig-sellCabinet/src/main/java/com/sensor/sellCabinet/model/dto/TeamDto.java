package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.Team;
import lombok.Data;

@Data
public class TeamDto extends Team {
	private String proxyName;
	private String firstProxyName;
	private String secondProxyName;
	private String thirdProxyName;
	private String fourthProxyName;
	private String fifthProxyName;
}
