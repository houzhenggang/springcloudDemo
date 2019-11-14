package com.sensor.sellCabinet.util;

import com.sensor.sellCabinet.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 唯一码生成策略工具类
 */
@Component
public class CodeUtil {

	@Autowired
	private SequenceService sequenceService;

	public static CodeUtil codeUtil;

	@PostConstruct
	public void init(){
		codeUtil = this;
		codeUtil.sequenceService = this.sequenceService;
	}

	public static String getPrimaryKey(String name){
		return codeUtil.sequenceService.getPrimaryKey(name);
	}
}
