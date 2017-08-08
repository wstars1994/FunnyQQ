package com.boomzz.test;

import java.io.InputStream;

import com.boomzz.core.IQQListener;
import com.boomzz.core.message.Message;
import com.boomzz.core.message.model.MMsgAccept;
import com.boomzz.core.message.model.MMsgSend;
import com.boomzz.core.model.MDiscus;
import com.boomzz.core.model.MFriends;
import com.boomzz.core.model.MGroup;
import com.boomzz.util.DateTimeUtil;

public class QQListener implements IQQListener{

	private String lastSendMsg = "";
	private String fromUin = "";
	
	@Override
	public void imageStream(InputStream inputStream) {
		System.out.println("获取到二维码资源");
	}


	@Override
	public void acceptMessage(MMsgAccept model, Message message) {
		if(lastSendMsg.equals(model.getMsg())){
			fromUin=model.getSendUin();
		}
		if(model!=null&&model.getMsgType()==1){
			MFriends mFriends = message.getFrientList().get(model.getFromUin());
			String name = mFriends.getMarkName()==null?mFriends.getNickName():mFriends.getMarkName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		if(model!=null&&model.getMsgType()==4){
			MGroup group = message.getGroupList().get(model.getFromUin());
			if(model.getSendUin().equals(fromUin)||!group.getName().equals("战略忽悠局 - SFYA"))
				return;
			else{
				MMsgSend m = new MMsgSend();
				String reply = Turing.reply(model.getMsg());
				if(lastSendMsg.equals(""))
					lastSendMsg = reply;
				m.setContent(reply);
				m.setUin(model.getFromUin());
				message.sendGroupMessage(m);
			}
			String name = group.getName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		if(model!=null&&model.getMsgType()==5){
			MDiscus group = message.getDiscusList().get(model.getFromUin());
			String name = group.getName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		
	}

}
