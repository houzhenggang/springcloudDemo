package com.sensor.sellCabinet.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class JsonMap {
	private String returnMsg;//返回信息
	private String returnCode;//返回代码
	private String returnJson;//返回数据
	
	public JsonMap(String returnMsg ,String returnCode ,String  retnrnJson){
		this.returnMsg = returnMsg;
		this.returnCode = returnCode;
		this.returnJson = retnrnJson;
	}
	public JsonMap(){
	}
	
	
	
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnJson() {
		return returnJson;
	}
	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}
	public  String toString(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("returnMsg", this.returnMsg);
		jsonObject.put("returnCode", this.returnCode);
		jsonObject.put("returnJson", this.returnJson);
		return jsonObject.toString() ;
	}
	public  Map<String,Object> toMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("returnMsg", this.returnMsg);
		map.put("returnCode", this.returnCode);
		map.put("returnJson", this.returnJson);
		return map;
	}
	
	

}
