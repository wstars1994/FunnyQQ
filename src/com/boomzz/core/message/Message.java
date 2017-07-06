package com.boomzz.core.message;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.FQQ;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

public class Message extends Thread{
	
	private FQQ fqq;
	
	public Message(FQQ fqq){
		this.fqq = fqq;
	}
	
	@Override
	public void run() {
		
		while(true){
			Map<String, String> params=new HashMap<>();
			params.put("ptwebqq", FQQ.loginModel.getPtwebqq());
			params.put("psessionid", FQQ.loginModel.getPsessionid());
			String url=FQQUtil.replace(Config.PARAM_MESSAGE_POLL, params);
			params.clear();
			params.put("r",url);
			String back=HttpClient.postHttps(Config.URL_POST_NEWMESSAGE, params, FQQ.cookies);
			System.out.println(back);
		}
	}
	
}
