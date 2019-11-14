package com.sensor.sellCabinet.service.impl;

import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.sensor.sellCabinet.mapper.UserMapper;
import com.sensor.sellCabinet.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userInfoSrvice")
@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

	private UserMapper userMapper;

	@Override
	public SysUser getSysUserInfo() {
		Integer userId = SecurityUtils.getUser().getId();
		return userMapper.getSysUser(userId);
	}

	@Override
	public List<SysUser> getSysUserList() {
		return userMapper.getSysUser();
	}

	@Override
	public Integer updateOpenId(Integer userId, String wxopenId,String realName) {
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setWxOpenid(wxopenId);
		sysUser.setRealName(realName);
		return userMapper.updateById(sysUser);
	}
}
