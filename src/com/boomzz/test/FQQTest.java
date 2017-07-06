package com.boomzz.test;

import com.boomzz.core.FQQ;
import com.boomzz.core.QRCodeLogin;
import com.boomzz.core.message.Message;

public class FQQTest {

	public static void main(String[] args) {
		FQQ fqq = new QRCodeLogin();
		Message msg = fqq.login();
	}
}
