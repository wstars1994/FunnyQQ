package com.boomzz.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.boomzz.core.Config;
import com.boomzz.core.FQQBase;
import com.boomzz.core.model.FriendsModel;
import com.boomzz.core.model.InfoModel;
import com.boomzz.core.model.PtuiCBMsgModel;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClientUtil;

/**
 * @author WStars
 *
 */
public class QRCodeLogin extends FQQBase{

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

	public void login(final String url) {
		System.out.println("开始登录轮询");
		Thread polling=new Thread(new Runnable() {
			@Override
			public void run() {
				boolean flag=true;
				while(flag){
					try {
						String back=HttpClientUtil.get(url,cookies);
						//转换成类
						PtuiCBMsgModel ptuiCBMsgModel=FQQUtil.ptuiCBMsgToModel(back);
						if(ptuiCBMsgModel==null){
							System.out.println("登录轮询失败");
							flag=false;
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
								//获取必须的Vfwebqq
								back=HttpClientUtil.get(FQQUtil.replace(com.boomzz.core.Config.URL_GET_VFWEBQQ+DateTimeUtil.getTimestamp(), "ptwebqq",loginModel.getPtwebqq()), cookies);
								loginModel.setVfwebqq(FQQUtil.jsonVfwebqq(back));
								//第二次登录验证
								Map<String, String> params=new HashMap<>();
								params.put("r", FQQUtil.replace(Config.PARAM_LOGIN2, "ptwebqq",loginModel.getPtwebqq()));
								back=HttpClientUtil.post(Config.URL_POST_LONGIN2, params, cookies);
								Map<String, String> map=FQQUtil.jsonLogin(back);
								if(map!=null){
									System.out.println("正式登陆成功");
									loginModel.setPsessionid(map.get("psessionid"));
								}
								
								while(flag)
								{
									System.out.println(">> 欢迎您 "+loginModel.getNickName());
									System.out.println(">> 选择一个项目");
									System.out.println(">> 1.获取个人信息");
									System.out.println(">> 2.获取好友列表");
									System.out.println(">> 3.获取在线好友列表");
									System.out.println(">> 4.获取最近联系的好友列表");
									System.out.println(">> 5.获取群列表");
									System.out.println(">> 6.获取讨论组列表");
									System.out.println(">> 7.退出");
									System.out.print(">> 请选择 : ");
									Scanner sc = new Scanner(System.in);
									String line = sc.nextLine();
									switch(line){
									case "1":
										InfoModel info=getSelfInfo();
										if(info!=null){
											System.out.println(info.getBirthday());
											System.out.println(info.getBlood());
											System.out.println(info.getGender());
											System.out.println(info.getConstel());
											System.out.println(info.getShengxiao());
										}
										break;
									case "2":
										List<FriendsModel> frientList = getFrientList();
										System.out.println("count:"+frientList.size());
										for(FriendsModel model : frientList){
											System.out.println(model.toString());
										}
										break;
									case "3":
										getOnlineFrientList();
										break;
									case "4":
										getRecentFrientList();
										break;
									case "5":
										getGroupList();
										break;
									case "6":
										getDiscusList();
										break;
									case "7":
										System.out.print(">> 退出成功");
										flag=false;
										break;
									}
								}
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
		QRCodeLogin funnyQQ=new QRCodeLogin();
		boolean status=funnyQQ.getQRCodeForMobile();
		if(status){
			funnyQQ.login(FQQUtil.replace(Config.URL_GET_LOGIN_POLLING, "ptqrtoken",funnyQQ.getPtqrToken()));
		}
	}
}
