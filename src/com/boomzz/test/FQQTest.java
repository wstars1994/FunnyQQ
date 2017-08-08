package com.boomzz.test;

import com.boomzz.core.login.AbstractLogin;
import com.boomzz.core.login.QRCodeLogin;
import com.boomzz.core.message.Message;

public class FQQTest {

	public static void main(String[] args) {
		//获取实例
		AbstractLogin qrLogin = new QRCodeLogin(new QQListener());
		//登录并返回Message实例  此类可以获得好友群讨论组信息以及发送消息
		Message msg = qrLogin.login(2);
		if(msg==null){
			System.out.println("登录失败,请查看登录日志");
		}else {
			System.out.println("登录成功");
		}
	}
}
