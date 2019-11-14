package com.sensor.sellCabinet.service;

import com.pig4cloud.pig.common.core.util.R;
import net.sf.json.JSONObject;

import java.util.List;

public interface WeChatService {
	List<String> encrypt(List<Integer> paramList);
}
