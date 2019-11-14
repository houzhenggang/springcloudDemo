package com.sensor.sellCabinet.model.dto;

public enum PayStatus {
	//未付款
	NOPAY("0"),
	//付款成功
	PAYED("1"),
	//退款中
	BAKPAYING("2"),
	//退款成功
	BAKPAYED("3"),
	//退款失败
	REFUSED("4");

	String value;

	PayStatus(String value) {
		this.value = value;
	}

	public static PayStatus fromTypeName(String typeName) {
		for (PayStatus type : PayStatus.values()) {
			if (type.getTypeName().equals(typeName)) {
				return type;
			}
		}
		return null;
	}

	public String getTypeName() {
		return this.value;
	}
}
