package com.sensor.sellCabinet.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 唯一码生成规则表
 *
 * @author chengpan
 * @date 2019-04-17 11:02:03
 */
public interface SequenceMapper{

	String getPrimaryKey(@Param("name") String name);
}
