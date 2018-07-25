package com.boomzz.test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.boomzz.core.IQQListener;
import com.boomzz.core.message.Message;
import com.boomzz.core.message.model.MMsgAccept;
import com.boomzz.core.message.model.MMsgSend;
import com.boomzz.core.model.MBase;
import com.boomzz.core.model.MDiscus;
import com.boomzz.core.model.MFriends;
import com.boomzz.core.model.MGroup;
import com.boomzz.util.DateTimeUtil;

public class QQListener implements IQQListener{

	private List<String> whiteList = Arrays.asList("战略忽悠局 - ZFYA","FunnyQQ Test Group");
	
	@Override
	public void imageStream(InputStream inputStream) {
		System.out.println("获取到二维码资源");
	}

	@Override
	public void acceptMessage(MMsgAccept model, Message message) {
		if(model!=null&&model.getMsgType()==1){
			MFriends mFriends = message.getFrientList().get(model.getFromUin());
			String name = mFriends.getMarkName()==null?mFriends.getNickName():mFriends.getMarkName();
			System.out.println("[个人] "+DateTimeUtil.timestampFormat(model.getTime()*1000)+" ["+name+"] :"+model.getMsg());
		}
		if(model!=null&&model.getMsgType()==4){
			MGroup group = message.getGroupList().get(model.getFromUin());
			String name = group.getName();
			MBase mBase = group.getMember().get(model.getSendUin());
			String sendName="";
			if(mBase!=null)
				sendName = mBase.getNickName();
			System.out.println("[群] "+DateTimeUtil.timestampFormat(model.getTime()*1000)+" ["+name+" | "+sendName+"] :"+model.getMsg());
			
			if(!whiteList.contains(group.getName()))
				return;
			else{
				if(model.isAt()){
					MMsgSend m = new MMsgSend();
					String reply = Turing.reply(model.getMsg());
					m.setContent(reply);
					m.setUin(model.getFromUin());
					message.sendGroupMessage(m);
				}
			}
		}
		if(model!=null&&model.getMsgType()==5){
			MDiscus group = message.getDiscusList().get(model.getFromUin());
			String name = group.getName();
			System.out.println("[讨论组] "+DateTimeUtil.timestampFormat(model.getTime()*1000)+" ["+name+"] :"+model.getMsg());
		}
		
	}

	@Override
	public void sysout(String msg) {
		System.out.println(msg);
	}

}
