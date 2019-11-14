package com.pig4cloud.pig.common.core.util;

import cn.hutool.json.JSONObject;
public class SensorString {
	
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z" };
	
	public static Boolean hashEmpty(String param){
		if(null!=param&&param.trim().length()>0){
			return true;
		}else {
			return false;
		}
	}
	
	 public static String queryToJson(String queryString){
		 if(!hashEmpty(queryString)){
				return "{}";
			 }
		   queryString = queryString.replaceAll("&", ",");
		   String[] query = queryString.split(",");
		   JSONObject json = new JSONObject();
		   for(String str:query){
		    	 int size = str.indexOf("=");
		    	 json.put(str.substring(0, size), str.substring(size+1));
		   }
		   return json.toString();
	}
	 
	public  static String randString(int size){
		StringBuffer str = new StringBuffer();
		for(int i=0;i<size;i++){
			int rand = (int)(Math.random()*3700);
			rand =rand%36;
			str.append(hexDigits[rand]);
		}
		return str.toString().toUpperCase();
	}
	public static String toHexString(byte[]  hex) {
		String str = "";
		for(byte b:hex){
			int i = b & 0xFF;
			if(i<16){
				str+="0"+Integer.toString(i, 16);
			}else{
				str+=Integer.toString(i, 16);
			}
		}
		return str;
	}
}
