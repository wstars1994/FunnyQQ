package com.boomzz.test;

import com.boomzz.core.login.QRCodeLogin;
import com.boomzz.core.message.Message;

public class FQQTest {

	public static void main(String[] args) {
		//获取实例
		QRCodeLogin qrLogin = new QRCodeLogin();
		//添加二维码监听
		qrLogin.addQRImageListener(new QRImageListener());
		//登录并返回Message实例  此类可以获得好友群讨论组信息以及发送消息
		Message msg = qrLogin.login();
		//添加新消息监听
		msg.addAcceptListener(new MsgAcceptListener(msg));
	}
}
