package com.boomzz.test;

import com.boomzz.core.login.AbstractLogin;
import com.boomzz.core.login.QRCodeLogin;
import com.boomzz.core.message.Message;
import com.boomzz.core.message.MessageSendModel;

public class FQQTest {

	public static void main(String[] args) {
		AbstractLogin fqq = new QRCodeLogin();
		Message msg = fqq.login();
		if(msg!=null){
//			Map<String, FriendsModel> frientList = msg.getFrientList();
			MessageSendModel messageModel=new MessageSendModel();
			messageModel.setContent("test");
			messageModel.setUin("3735856765");
			msg.sendMessage(messageModel);
		}
	}
}
