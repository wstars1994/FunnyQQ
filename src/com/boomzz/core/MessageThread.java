package com.boomzz.core;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

public class MessageThread extends Thread{
	@Override
	public void run() {
		
		while(true){
			Map<String, String> params=new HashMap<>();
			params.put("vfwebqq", FQQ.loginModel.getVfwebqq());
			params.put("psessionid", FQQ.loginModel.getPsessionid());
			String url=FQQUtil.replace(Config.PARAM_MESSAGE_POLL, params);
			params.clear();
			params.put("r",url);
			String back=HttpClient.post(Config.URL_POST_LONGIN2, params, FQQ.cookies);
			System.out.println(back);
		}
	}
}
