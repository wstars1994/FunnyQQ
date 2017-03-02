package com.boomzz.test;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.FunnyQQBase;
import com.boomzz.core.IQRCodeLogin;
import com.boomzz.model.PtuiCBMsgModel;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FunnyQQUtil;
import com.boomzz.util.HttpClientUtil;

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
			HttpClientUtil.getBackAndCookieForQR(Config.URL_GET_QR+Math.random(),Config.FILE_PATH_QR,cookies,Config.FILE_IMG_LOCAL);
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
	public void loginPolling(final String url) {
		System.out.println("开始登录轮询");
		Thread polling=new Thread(new Runnable() {
			@Override
			public void run() {
				boolean flag=true;
				while(flag){
					try {
						String back=HttpClientUtil.get(url,cookies);
						//转换成类
						PtuiCBMsgModel ptuiCBMsgModel=FunnyQQUtil.ptuiCBMsgToModel(back);
						if(ptuiCBMsgModel==null){
							System.out.println("登录轮询失败");
							flag=false;
						}else{
							System.out.println(ptuiCBMsgModel.getNo() +" : "+ ptuiCBMsgModel.getP4());
							if(ptuiCBMsgModel.getNo()==0){//初次登录成功
								loginModel.setNickName(ptuiCBMsgModel.getP5());
								loginModel.setUid(FunnyQQUtil.findParam(ptuiCBMsgModel.getP2(),"uin"));
								loginModel.setPtwebqq(FunnyQQUtil.findCookieParam("ptwebqq", cookies));
								loginModel.setClientId(Config.PARAM_CLIENTID);
								//第一次登录验证 获取必要参数
								String checkSigUrl=ptuiCBMsgModel.getP2();
								HttpClientUtil.get(checkSigUrl, cookies);
								//获取必须的Vfwebqq
								back=HttpClientUtil.get(FunnyQQUtil.replace(Config.URL_GET_VFWEBQQ+DateTimeUtil.getTimestamp(), "ptwebqq",loginModel.getPtwebqq()), cookies);
								loginModel.setVfwebqq(FunnyQQUtil.findParamVfwebqq(back));
								//第二次登录验证
								Map<String, String> params=new HashMap<>();
								params.put("r", FunnyQQUtil.replace(Config.PARAM_LOGIN2, "ptwebqq",loginModel.getPtwebqq()));
								back=HttpClientUtil.post(Config.URL_POST_LONGIN2, params, cookies);
								Map<String, String> map=FunnyQQUtil.jsonLogin(back);
								if(map!=null){
									System.out.println("正式登陆成功");
									loginModel.setPsessionid(map.get("psessionid"));
								}
								//登陆完毕
								getSelfInfo();
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
	}
	public static void main(String[] args) {
		
		IQRCodeLogin funnyQQ=new QRCodeLogin();
		boolean status=funnyQQ.getQRCodeForMobile();
		if(status){
			String url=FunnyQQUtil.replace(Config.URL_GET_LOGIN_POLLING, "ptqrtoken",funnyQQ.getPtqrToken());
			funnyQQ.loginPolling(url);
		}
	}
}
