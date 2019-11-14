package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.ProductInfo;
import lombok.Data;

@Data
public class ProductInfoDto extends ProductInfo {
	String sDate;
	String eDate;
	String operatorName;
	String proxyBusinessName;
}
