package com.boomzz.core;

import com.boomzz.core.model.PtuiCBMsgModel;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClientUtil;

/**
 * @author WStars
 *
 */
public class QRCodeLogin extends FQQ{

	/*
	 * 抓包时发现的
	 * 10:'online',20:'offline',30:'away',40:'hidden',50:'busy',60:'callme',70:'silent'
	 * 10:'在线',20:'离线',30:'离开',40:'隐身',50:'忙碌',60:'Q我吧',70:'请勿打扰' 
	 *
	 * 开发时间 2017/02/27 webqq版本适用 可能qq接口会变动
	 *
	 **/
	
	//  现在只开放了手Q扫描二维码登录
	
	
	private boolean getQRCodeForMobile() {
		try {
			HttpClientUtil.getBackAndCookieForQR(Config.URL_GET_QR+Math.random(),Config.FILE_PATH_QR,cookies,Config.FILE_IMG_LOCAL);
    		System.out.println("获取二维码成功");
    		return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return false;
	}

	private String getPtqrToken() {
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

	public boolean login_1() {
		if(cookies.size()!=0)
			return true;
		final QRCodeLogin funnyQQ=new QRCodeLogin();
		boolean status=funnyQQ.getQRCodeForMobile();
		if(status){
			System.out.println("开始登录轮询");
			while(true){
				try {
					String back=HttpClientUtil.get(FQQUtil.replace(Config.URL_GET_LOGIN_POLLING, "ptqrtoken",funnyQQ.getPtqrToken()),cookies);
					//转换成类
					PtuiCBMsgModel ptuiCBMsgModel=FQQUtil.ptuiCBMsgToModel(back);
					if(ptuiCBMsgModel==null){
						System.out.println("登录轮询失败");
						return false;
					}else{
						System.out.println(ptuiCBMsgModel.getNo() +" : "+ ptuiCBMsgModel.getP4());
						if(ptuiCBMsgModel.getNo()==0){//初次登录成功
							loginModel.setNickName(ptuiCBMsgModel.getP5());
							loginModel.setUin(FQQUtil.findParam(ptuiCBMsgModel.getP2(),"uin"));
							loginModel.setId(loginModel.getUin());
							loginModel.setPtwebqq(FQQUtil.findCookieParam("ptwebqq", cookies));
							loginModel.setClientId(Config.PARAM_CLIENTID);
							//第一次登录验证 获取必要参数
							String checkSigUrl=ptuiCBMsgModel.getP2();
							HttpClientUtil.get(checkSigUrl, cookies);
							return true;
						}
					}
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public void loginSuccess() {
		
	}
}
