package com.boomzz.core.message;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.message.model.MNewMSG;
import com.boomzz.core.model.MDiscus;
import com.boomzz.core.model.MFriends;
import com.boomzz.core.model.MGroup;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

public class TMSGAccept extends Message implements Runnable{

	@Override
	public void run() {
		while(true){
			Map<String, String> params=new HashMap<>();
			params.put("ptwebqq", loginModel.getPtwebqq());
			params.put("psessionid", loginModel.getPsessionid());
			String url=FQQUtil.replace(Config.PARAM_MESSAGE_POLL, params);
			params.clear();
			params.put("r",url);
			String back=HttpClient.postHttps(Config.URL_POST_NEWMESSAGE, params,cookies);
			newMessage(FQQUtil.jsonNewMessage(back));
		}
	}
	
	private void newMessage(MNewMSG model){
		if(model!=null&&model.getMsgType()==1){
			MFriends mFriends = getFrientList().get(model.getFromUin());
			String name = mFriends.getMarkName()==null?mFriends.getNickName():mFriends.getMarkName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		if(model!=null&&model.getMsgType()==4){
			MGroup group = getGroupList().get(model.getFromUin());
			String name = group.getName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		if(model!=null&&model.getMsgType()==5){
			MDiscus group = getDiscusList().get(model.getFromUin());
			String name = group.getName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
	}
}
