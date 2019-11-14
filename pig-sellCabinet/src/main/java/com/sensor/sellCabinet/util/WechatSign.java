package com.sensor.sellCabinet.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WechatSign {

	public static String getSing(Map<String, String> parm,String key){
		StringBuffer sb = new StringBuffer();
		String sign  = "";
		Collection<String> keyset= parm.keySet();   
		List list=new ArrayList<String>(keyset);  
		Collections.sort(list);
		for(int i=0;i<list.size();i++){  
			if("key".equals(list.get(i))){
				continue;
			}
			sb.append(list.get(i)+"="+parm.get(list.get(i)));  
			sb.append("&");
		}
		try {
			sign = WeChatUtils.getpaySign(sb,key);
		} catch (Exception e) {
			
		}
		return sign;
	}
	
}
