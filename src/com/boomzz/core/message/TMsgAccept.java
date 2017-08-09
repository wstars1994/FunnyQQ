package com.boomzz.core.message;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.IQQListener;
import com.boomzz.core.message.model.MMsgAccept;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

/**
 * @author WStars
 * 接收消息线程(长连接)
 */
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
			params.put("ptwebqq", Message.loginModel.getPtwebqq());
			params.put("psessionid", Message.loginModel.getPsessionid());
			String url=FQQUtil.replace(Config.PARAM_MESSAGE_POLL, params);
			params.clear();
			params.put("r",url);
			String back=HttpClient.postHttps(Config.URL_POST_NEWMESSAGE, params,message.cookies);
			MMsgAccept me = FQQUtil.jsonNewMessage(back);
			if(me!=null)
				listener.acceptMessage(me,message);
		}
	}
}
