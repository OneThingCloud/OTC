package com.hyper.core.util;

import com.alibaba.fastjson.JSONObject;

/** 
 * @ClassName  R
 * @author  泡面和尚
 * @date  2017年12月22日 下午12:37:02
 */
public class R {
	
	private static final String KEK_STATUS = "status";
	private static final String KEK_MESSAGE = "msg";
	private static final String OK = "ok";
	private static final String ERROR = "error";
	
	public static JSONObject ok(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(KEK_STATUS, OK);
		return jsonObject;
	}
	
	public static JSONObject error(String...msg){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(KEK_STATUS, ERROR);
		jsonObject.put(KEK_MESSAGE, msg);
		return jsonObject;
	}
	
	public static JSONObject to(boolean flag, String...msg){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(KEK_STATUS, flag? OK:ERROR);
		jsonObject.put(KEK_MESSAGE, msg);
		return jsonObject;
	}
 
}
