package com.boomzz.test;

import com.boomzz.core.login.QRCodeLogin;
import com.boomzz.core.message.Message;

public class FQQTest {

	public static void main(String[] args) {
		QRCodeLogin qrLogin = new QRCodeLogin();
		qrLogin.addQRImageListener(new QRImageListener());
		
		Message msg = qrLogin.login();
		msg.addAcceptListener(new MsgAcceptListener(msg));
	}
}
