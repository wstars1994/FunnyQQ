package com.boomzz.core.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boomzz.core.Config;
import com.boomzz.core.model.PtuiCBMsgModel;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

/**
 * @author WStars
 *
 */
public class QRCodeLogin extends AbstractLogin{

	private final Logger logger = LogManager.getLogger();
	
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
			HttpClient.getBackAndCookieForQR(Config.URL_GET_QR+Math.random(),Config.FILE_PATH_QR,cookies,Config.FILE_IMG_LOCAL);
			logger.info("获取二维码成功");
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
		final QRCodeLogin funnyQQ=new QRCodeLogin();
		boolean status=funnyQQ.getQRCodeForMobile();
		if(status){
			logger.info("开始登录轮询");
			while(true){
				try {
					String back=HttpClient.get(FQQUtil.replace(Config.URL_GET_LOGIN_POLLING, "ptqrtoken",funnyQQ.getPtqrToken()),cookies);
					//转换成类
					PtuiCBMsgModel ptuiCBMsgModel=FQQUtil.ptuiCBMsgToModel(back);
					if(ptuiCBMsgModel==null){
						logger.error("登录轮询失败");
						return false;
					}else{
						logger.info(ptuiCBMsgModel.getNo() +" : "+ ptuiCBMsgModel.getP4());
						if(ptuiCBMsgModel.getNo()==0){//初次登录成功
							loginModel.setNickName(ptuiCBMsgModel.getP5());
							loginModel.setUin(FQQUtil.findParam(ptuiCBMsgModel.getP2(),"uin"));
							loginModel.setId(loginModel.getUin());
							loginModel.setPtwebqq(FQQUtil.findCookieParam("ptwebqq", cookies));
							loginModel.setClientId(Config.PARAM_CLIENTID);
							//第一次登录验证 获取必要参数
							String checkSigUrl=ptuiCBMsgModel.getP2();
							HttpClient.get(checkSigUrl, cookies);
							return true;
						}
					}
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		return false;
	}

//	@Override
//	public void loginSuccess() {
//		while(true)
//		{
//			System.out.println(">> 欢迎您 "+loginModel.getNickName());
//			System.out.println(">> 选择一个项目");
//			System.out.println(">> 1.获取个人信息");
//			System.out.println(">> 2.获取好友列表");
//			System.out.println(">> 3.获取在线好友列表");
//			System.out.println(">> 4.获取最近联系的好友列表");
//			System.out.println(">> 5.获取群列表");
//			System.out.println(">> 6.获取讨论组列表");
//			System.out.println(">> 7.退出");
//			System.out.print(">> 请选择 : ");
//			Scanner sc = new Scanner(System.in);
//			String line = sc.nextLine();
//			switch(line){
//			case "1":
//				InfoModel info=getSelfInfo();
//				if(info!=null){
//					System.out.println(info.getBirthday());
//					System.out.println(info.getBlood());
//					System.out.println(info.getGender());
//					System.out.println(info.getConstel());
//					System.out.println(info.getShengxiao());
//				}
//				break;
//			case "2":
//				Map<String, FriendsModel> map = getFrientList();
//				for(String uin : map.keySet()){
//					System.out.println(map.get(uin).toString());
//				}
//				break;
//			case "3":
//				List<FriendsModel> frientList = frientList = getOnlineFrientList();
//				for(FriendsModel model : frientList){
//					System.out.println(model.toString());
//				}
//				break;
//			case "4":
//				getRecentFrientList();
//				break;
//			case "5":
//				List<GroupModel> groupList = getGroupList();
//				for(GroupModel m:groupList)
//					System.out.println(m.toString());
//				break;
//			case "6":
//				List<DiscusModel> discusList = getDiscusList();
//				for(DiscusModel m:discusList)
//					System.out.println(m.toString());
//				break;
//			case "7":
//				System.out.print(">> 退出成功");
//				return;
//			}
//		}
//	}
}
