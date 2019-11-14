package com.sensor.sellCabinet.service.impl;

import com.sensor.sellCabinet.mapper.SequenceMapper;
import com.sensor.sellCabinet.service.SequenceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 唯一码生成规则表
 *
 * @author chengpan
 * @date 2019-04-17 09:53:59
 */
@Service("sequenceService")
@AllArgsConstructor
public class SequenceServiceImpl implements SequenceService {

	/**
	 * field注入，不推荐使用
	 */
//	@Autowired
//	private SequenceMapper sequenceMapper;

	/**
	 * 构造器注入
	 */
	private SequenceMapper sequenceMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String getPrimaryKey(String name) {
		return sequenceMapper.getPrimaryKey(name);
	}
}
