package com.sensor.sellCabinet.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.sensor.sellCabinet.util.AppEncrypt;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/appEncrypt")
public class EncryptController
{
	@GetMapping("/encryptAES")
	public R encryptAES(@Param("password")String password) throws Exception{
		String res = AppEncrypt.encryptAES(password);
		if (res!=null&&res!=""){
		return new R<>(res);
		}
		return new R(Boolean.FALSE,"加密失败");
	}
}
