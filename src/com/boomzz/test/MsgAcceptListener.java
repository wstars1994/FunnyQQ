package com.boomzz.test;

import com.boomzz.core.message.IMessageAcceptListener;
import com.boomzz.core.message.Message;
import com.boomzz.core.message.model.MMsgAccept;
import com.boomzz.core.message.model.MMsgSend;
import com.boomzz.core.model.MDiscus;
import com.boomzz.core.model.MFriends;
import com.boomzz.core.model.MGroup;
import com.boomzz.util.DateTimeUtil;

public class MsgAcceptListener implements IMessageAcceptListener{

	private Message message;
	public MsgAcceptListener(){}
	public MsgAcceptListener(Message msg){
		this.message = msg;
	}
	
	@Override
	public void acceptMessage(MMsgAccept model) {
		
		if(model!=null&&model.getMsgType()==1){
			MFriends mFriends = message.getFrientList().get(model.getFromUin());
			String name = mFriends.getMarkName()==null?mFriends.getNickName():mFriends.getMarkName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		if(model!=null&&model.getMsgType()==4){
			MGroup group = message.getGroupList().get(model.getFromUin());
			if(model.getMsg().equals("小武是菜鸡,内战猛如虎,外战怂如狗")||!group.getName().equals("战略忽悠局 - SFYA"))
				return;
			String name = group.getName();
			MMsgSend m = new MMsgSend();
			m.setContent("小武是菜鸡,内战猛如虎,外战怂如狗");
			m.setUin(model.getFromUin());
			message.sendGroupMessage(m);
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		if(model!=null&&model.getMsgType()==5){
			MDiscus group = message.getDiscusList().get(model.getFromUin());
			String name = group.getName();
			System.out.println(DateTimeUtil.timestampFormat(model.getTime()*1000)+" "+name+":"+model.getMsg());
		}
		
	}

}
