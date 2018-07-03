package com.boomzz.test;

import com.boomzz.core.Config;
import com.boomzz.core.login.AbstractLogin;
import com.boomzz.core.login.QRCodeLogin;
import com.boomzz.core.message.Message;

public class FQQTest {

	public static void main(String[] args) {
//		//关闭本地生成图片
//		Config.setFILE_IMG_LOCAL(true);
//		//关闭缓存登录
//		Config.setAUTO_LOGIN(false);
		//获取实例
		AbstractLogin qrLogin = new QRCodeLogin(new QQListener());
		//登录并返回Message实例  此类可以获得好友群讨论组信息以及发送消息

		int loginType = 1; //登录方式 1:重新扫码登录;2:缓存登录(可能会超时失效)
		Message msg = qrLogin.login(loginType);
		if(msg==null){
			System.out.println("登录失败,请查看登录日志");
		}else {
			System.out.println("登录成功");
		}
	}
}
