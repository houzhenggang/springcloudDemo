package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.RunCabinetProduct;
import lombok.Data;

import java.util.List;

@Data
public class RunCabinetProductV {
	private List<RunCabinetProduct> runCabinetProductDtoList;
}
