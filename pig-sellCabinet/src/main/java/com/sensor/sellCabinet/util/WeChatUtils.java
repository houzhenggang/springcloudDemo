package com.sensor.sellCabinet.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WeChatUtils {
	public static String getpaySign(StringBuffer sb,String key) throws NoSuchAlgorithmException{
		sb.append("key=" + key);
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(sb.toString().getBytes());
        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
//		String sign = new BigInteger(1, md5.digest()).toString(16).toUpperCase();
		String sign = KeysUtil.encodeByMD5(sb.toString()).toUpperCase();
		return sign;
	}
	
	public static String mapToXml(Map<String,String> paraMap){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = paraMap.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"key".equals(k)) {
				sb.append("<"+k+">" + v +"</"+k+">");
			}
		}
		sb = sb.append("</xml>");
		return sb.toString();
	}
	public static long genTimeStamp(){
		return System.currentTimeMillis()/1000;
	}
}
