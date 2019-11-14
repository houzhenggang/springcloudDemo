package com.sensor.sellCabinet.service;

import com.pig4cloud.pig.admin.api.entity.SysUser;

import java.util.List;

/**
 * 用户接口
 */
public interface UserInfoService {
	/**
	 * 用户基本信息
	 * @return
	 */
	SysUser getSysUserInfo();

	List<SysUser> getSysUserList();

	Integer updateOpenId(Integer userId, String openId,String realName);
}
