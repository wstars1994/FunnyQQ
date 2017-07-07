package com.boomzz.core.message;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.message.model.MMsgAccept;
import com.boomzz.core.model.MDiscus;
import com.boomzz.core.model.MFriends;
import com.boomzz.core.model.MGroup;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

public final class TMsgAccept implements Runnable{

	private IMessageAcceptListener listener;
	private Message message;
	
	public TMsgAccept(Message message,IMessageAcceptListener listener) {
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
			listener.acceptMessage(FQQUtil.jsonNewMessage(back));
		}
	}
}