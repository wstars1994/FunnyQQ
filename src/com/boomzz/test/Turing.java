package com.boomzz.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.boomzz.util.HttpClient;

import net.sf.json.JSONObject;

public class Turing {

	private static String url = "http://www.tuling123.com/openapi/api?key=11ba892c7aad6cb011a8198df1437113&info=";
	public static String reply(String msg){
		try {
			String INFO = URLEncoder.encode(msg, "utf-8");
			String post = HttpClient.get(url+INFO, null);
			JSONObject fromObject = JSONObject.fromObject(post);
			return fromObject.getString("text");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return msg; 
	}
}
