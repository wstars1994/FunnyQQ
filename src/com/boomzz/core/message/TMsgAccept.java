package com.boomzz.core.message;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.IQQListener;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

public final class TMsgAccept implements Runnable{

	private IQQListener listener;
	private Message message;
	
	public TMsgAccept(Message message,IQQListener listener) {
		this.message = message;
		this.listener=listener;
	}

	@Override
	public void run() {
		while(true){
			Map<String, String> params=new HashMap<>();
			params.put("ptwebqq", message.loginModel.getPtwebqq());
			params.put("psessionid", message.loginModel.getPsessionid());
			String url=FQQUtil.replace(Config.PARAM_MESSAGE_POLL, params);
			params.clear();
			params.put("r",url);
			String back=HttpClient.postHttps(Config.URL_POST_NEWMESSAGE, params,message.cookies);
			listener.acceptMessage(FQQUtil.jsonNewMessage(back),message);
		}
	}
}
