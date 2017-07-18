package com.boomzz.core.login;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boomzz.core.Config;
import com.boomzz.core.model.MPtuiCBMsg;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

/**
 * @author WStars
 *
 */
public final class QRCodeLogin extends AbstractLogin{

	private final Logger logger = LogManager.getLogger();
	
	private IQRImageListener listener = null;
	
	private boolean getQRCodeForMobile() {
		try {
			InputStream inputStream = HttpClient.getBackAndCookieForQR(Config.URL_GET_QR+Math.random(),Config.FILE_PATH_QR,cookies,Config.FILE_IMG_LOCAL);
			if(listener!=null)
				listener.imageStream(inputStream);
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

	protected boolean login_1() {
		boolean status=getQRCodeForMobile();
		if(status){
			logger.info("开始登录轮询");
			while(true){
				try {
					String back=HttpClient.get(FQQUtil.replace(Config.URL_GET_LOGIN_POLLING, "ptqrtoken",getPtqrToken()),cookies);
					//转换成类
					MPtuiCBMsg ptuiCBMsgModel=FQQUtil.ptuiCBMsgToModel(back);
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
	
	public void addQRImageListener(IQRImageListener listener) {
		this.listener = listener;
	}
}
