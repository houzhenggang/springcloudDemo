package com.sensor.sellCabinet.service;

import java.io.IOException;

import com.pig4cloud.pig.common.core.util.R;

public interface WebSocketService {

	 public R sendMessage(String param) throws IOException;

}
