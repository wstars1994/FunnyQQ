package com.boomzz.test;

import com.boomzz.core.login.QRCodeLogin;
import com.boomzz.core.message.Message;

public class FQQTest {

	public static void main(String[] args) {
		Message msg = new QRCodeLogin().login();
	}
}
