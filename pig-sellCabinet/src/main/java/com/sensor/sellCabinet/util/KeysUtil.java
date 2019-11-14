package com.sensor.sellCabinet.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import com.pig4cloud.pig.common.core.util.SensorString;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KeysUtil {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private final static String[] keyArray = { "s", "e", "n", "s", "o", "r", "6", "6", "8", "y", "i", "j", "i", "a",
			"n", "q","i", "d","o", "n","g" };
	
	// 十六进制下数字到字符的映射数组

	/** 把inputString加密 */
	public static String createPassword(String inputString) {
		return encodeByMD5(inputString);
	}
	
	/** 把将key解密出value */
	public static String getValue(String key) {
		String values = "";
		for(int i = 2;i<key.length();i++){
			if(isPrime(i)){
				values += keyArray[Integer.valueOf(key.substring(i, i+2))];
				i++;
			}else {
				values +=keyArray[Integer.valueOf(key.substring(i,i+1))];
			}
		}
		return values;
	}
	/**随机获取Key**/
	public static String getKey(){
		String key = "";
		for(int i =0 ;i<24;i++){
			if(isPrime(i)){
				int j = (int)(Math.random()*20);
				key += j>=10?j:"0"+j;
				i++;
			}else {
				key += (int)(Math.random()*10);
			}
		}
		return key;
	}
	

	/**
	 * 验证输入的密码是否正确
	 * 
	 * @param password
	 *            真正的密码（加密后的真密码）
	 * @param inputString
	 *            输入的字符串
	 * @return 验证结果，boolean类型
	 */
	public static boolean authenticatePassword(String password, String inputString) {
		if (password.equals(encodeByMD5(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/** 对字符串进行MD5编码 */
	public static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// 创建具有指定算法名称的信息摘要
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
				byte[] results = md.digest(originString.getBytes());
				// 将得到的字节数组变成字符串返回
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 轮换字节数组为十六进制字符串
	 * 
	 * @param b
	 *            字节数组
	 * @return 十六进制字符串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将一个字节转化成十六进制形式的字符串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static boolean isPrime(int a) {  
        boolean flag = true;  
        if (a < 2) {  
            return false;  
        } else {  
            for (int i = 2; i <= Math.sqrt(a); i++) {  
                if (a % i == 0) {
                    flag = false;  
                    break;  
                }  
            }  
        }  
        return flag;  
    }  
	
	//检查参数是否正确
	public static Boolean checkSign(JSONObject jsonObject){
		Boolean returnBoolean = false;
		String key = jsonObject.containsKey("random") ? jsonObject.getString("random") : "";
		String type = jsonObject.containsKey("type") ? jsonObject.getString("type") : "";
		String value = getValue(key);
		String sign = jsonObject.containsKey("sign") ? jsonObject.getString("sign") : "";
		jsonObject.remove("sign");
		jsonObject.remove("random");
		if(!SensorString.hashEmpty(type)){
			returnBoolean = KeysUtil.authenticatePassword(sign, jsonObject.toString() + value);
		}else if(SensorString.hashEmpty(type)&&"IOS".equals(type)){
			JSONObject js = new JSONObject();
			String className = jsonObject.containsKey("className") ? jsonObject.getString("className") : "";
			String parameter = jsonObject.containsKey("parameter") ? jsonObject.getString("parameter") : "{}";
			String userId = jsonObject.containsKey("userId") ? jsonObject.getString("userId") : "";
			js.put("className", className);
			js.put("parameter", parameter);
			js.put("userId", userId);
			returnBoolean = KeysUtil.authenticatePassword(sign, js.toString() + value);
		}
		
		return returnBoolean;
	}
	//加密参数
	public static JSONObject encryption(JSONObject jsonObject){
		String key = getKey();
		String value = getValue(key);
		System.out.println("md5串:"+jsonObject.toString()+value);
		String sign = encodeByMD5(jsonObject.toString()+value);
		System.out.println("key:"+key);
		System.out.println("value:"+value);
		System.out.println("sign:"+sign);
		jsonObject.put("random", key);
		jsonObject.put("sign", sign);
		return jsonObject;
	}

}
