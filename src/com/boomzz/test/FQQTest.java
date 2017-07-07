package com.boomzz.test;

import com.boomzz.core.login.AbstractLogin;
import com.boomzz.core.login.QRCodeLogin;
import com.boomzz.core.message.MMSGSend;
import com.boomzz.core.message.Message;

public class FQQTest {

	public static void main(String[] args) {
		AbstractLogin fqq = new QRCodeLogin();
		Message msg = fqq.login();
//		if(msg!=null){
//			MMSGSend messageModel=new MMSGSend();
//			messageModel.setContent("test");
//			messageModel.setUin("2650379035");
//			msg.sendMessage(messageModel);
//		}
	}
}
