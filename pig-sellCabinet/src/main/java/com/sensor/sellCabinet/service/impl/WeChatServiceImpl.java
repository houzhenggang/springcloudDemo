package com.sensor.sellCabinet.service.impl;

import com.sensor.sellCabinet.service.WeChatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {


	@Override
	public List<String> encrypt(List<Integer> paramList) {
		char[] pbuf = new char[8];
		Random ra =new Random();
		pbuf[0] = (char)ra.nextInt(256);
		pbuf[1] = (char)paramList.get(0).intValue();
		pbuf[2] = (char)paramList.get(1).intValue();
		pbuf[3] = (char)paramList.get(2).intValue();
		pbuf[4] = (char)paramList.get(3).intValue();
		pbuf[5] = (char)paramList.get(4).intValue();
		pbuf[6] = (char)paramList.get(5).intValue();
		pbuf[7] = (char)paramList.get(6).intValue();
		PayCrypt pay = new PayCrypt();
		pay.pCrypt_Encrypt(pbuf);
		List<String> strList = new ArrayList<>();
		for(int i = 0; i < 8; i++)
		{
			strList.add(Integer.toHexString(pbuf[i]).toUpperCase());
		}
		return strList;
	}
}
