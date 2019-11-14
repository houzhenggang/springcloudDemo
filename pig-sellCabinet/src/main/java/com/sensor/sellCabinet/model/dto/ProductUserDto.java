package com.sensor.sellCabinet.model.dto;

import com.sensor.sellCabinet.model.entity.ProductUser;
import lombok.Data;

import java.util.List;

@Data
public class ProductUserDto {
	private List<ProductUser> productUserList;
}
