package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.CabinetInfo;
import lombok.Data;


@Data
public class CabinetInfoDto extends CabinetInfo {
	private String teamName;
	private String proxyName;
}
