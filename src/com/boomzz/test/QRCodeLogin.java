package com.boomzz.test;

import com.boomzz.core.Constant;
import com.boomzz.core.FunnyQQBase;
import com.boomzz.core.IQRCodeLogin;
import com.boomzz.model.PtuiCBMsgModel;
import com.boomzz.util.HttpClientUtil;
import com.boomzz.util.TencentBackMsgUtil;

/**
 * @author WStars
 *
 */
public class QRCodeLogin extends FunnyQQBase implements IQRCodeLogin{

	/*
	 * 抓包时发现的
	 * 10:'online',20:'offline',30:'away',40:'hidden',50:'busy',60:'callme',70:'silent'
	 * 10:'在线',20:'离线',30:'离开',40:'隐身',50:'忙碌',60:'Q我吧',70:'请勿打扰' 
	 *
	 * 开发时间 2017/02/27 webqq版本适用 可能qq接口会变动
	 *
	 **/
	
	@Override
	public boolean getQRCodeForMobile() {
		try {
			HttpClientUtil.getBackAndCookieForQR(Constant.URL_GET_QR+Math.random(),Constant.FILE_PATH_QR,cookies);
    		System.out.println("获取二维码成功");
    		return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return false;
	}

	@Override
	public String getPtqrToken() {
		String qrsig=null;
		for(String str:cookies.keySet()){
			if(str.equals("qrsig")){
				qrsig=cookies.get(str);
				loginModel.setQrSig(qrsig);
			}
		}
		int e=0,i=0,n=0;
		for (e = 0, i = 0, n = qrsig.length(); n > i; ++i){
			char s=qrsig.charAt(i);
			e += (e << 5) + (int)s;
		}
		return (2147483647 & e)+"";
	}

	@Override
	public boolean loginPolling(final String url) {
		System.out.println("开始登录轮询");
		Thread polling=new Thread(new Runnable() {
			@Override
			public void run() {
				boolean flag=true;
				while(flag){
					try {
						String back=HttpClientUtil.get(url,cookies);
						//转换成类
						PtuiCBMsgModel ptuiCBMsgModel=TencentBackMsgUtil.ptuiCBMsgToModel(back);
						if(ptuiCBMsgModel==null){
							System.out.println("登录轮询失败");
							flag=false;
						}else{
							System.out.println(ptuiCBMsgModel.getNo() +" : "+ ptuiCBMsgModel.getP4());
							if(ptuiCBMsgModel.getNo()==0){//初次登录成功
								loginModel.setNickName(ptuiCBMsgModel.getP5());
								//第一次登录验证
								
								//第二次登录验证
								
								flag=false;
							}
						}
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		polling.start();
		return true;
	}
	public static void main(String[] args) {
		
		IQRCodeLogin funnyQQ=new QRCodeLogin();
		boolean status=funnyQQ.getQRCodeForMobile();
		if(status){
			String url=Constant.URL_GET_LOGIN_POLLING.replace("#ptqrtoken#", funnyQQ.getPtqrToken());
			funnyQQ.loginPolling(url);
		}
	}
}
