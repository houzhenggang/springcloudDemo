package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 获取用户基本信息
 */
public interface UserMapper extends BaseMapper<SysUser> {

	SysUser getSysUser(@Param("userId") Integer userId);

	void updateByUserId(@Param("userId") String userId, @Param("money") BigDecimal money,@Param("type")String type);

	List<SysUser> getSysUser();

}
