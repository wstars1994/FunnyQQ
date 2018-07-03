package com.boomzz.core.login;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boomzz.core.Config;
import com.boomzz.core.IQQListener;
import com.boomzz.core.message.Message;
import com.boomzz.core.model.MLogin;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;
import com.boomzz.util.PropertiesUtil;

/**
 * @author WStars
 * 
 */
public abstract class AbstractLogin{

	private final Logger logger = LogManager.getLogger();
	//个人登录信息
	protected MLogin loginModel = new MLogin();
	//全局Cookie
	protected static Map<String, String> cookies = new HashMap<>();
	
	protected IQQListener listener = null;
	
	protected abstract boolean login_1();

	public AbstractLogin(IQQListener listener){
		this.listener = listener;
	}
	/**
	 * @param type 登录方式 1:重新扫码登录;2:缓存登录(可能会超时失效)
	 */
	public Message login(int type){
		try {
			if(type==2){
				loginModel.setPtwebqq(PropertiesUtil.GetValueByKey("ptwebqq"));
				loginModel.setId(PropertiesUtil.GetValueByKey("id"));
				loginModel.setClientId(PropertiesUtil.GetValueByKey("clientId"));
				loginModel.setNickName(PropertiesUtil.GetValueByKey("nickName"));
				loginModel.setUin(PropertiesUtil.GetValueByKey("uin"));
				String cookieStr = PropertiesUtil.GetValueByKey("cookie");
				String cookiesArry[]=cookieStr.split(";");
				for(String strCookie:cookiesArry){
					String c[]=strCookie.split("=");
					if(c.length==2){
						cookies.put(c[0], c[1]);
					}
				}
//				第一次登陆 表现为登录方式 cookie获取完毕 相应值已经获取并设置
			}else if(type==1){
				login_1();
			}
			String back=HttpClient.get(FQQUtil.replace(Config.URL_GET_VFWEBQQ+DateTimeUtil.getTimestamp(), "ptwebqq",loginModel.getPtwebqq()), cookies);
			String jsonVfwebqq = FQQUtil.jsonVfwebqq(back);
			if(jsonVfwebqq==null){
				logger.error("登录失败:获取vfwebqq失败,可能是登录缓存过期,请使用非缓存方式登录");
				return null;
			}
			loginModel.setVfwebqq(jsonVfwebqq);
			//第二次登录验证
			Map<String, String> params=new HashMap<>();
			params.put("r", FQQUtil.replace(Config.PARAM_LOGIN2, "ptwebqq",loginModel.getPtwebqq()));
			back=HttpClient.post(Config.URL_POST_LONGIN2, params, cookies);
			Map<String, String> map=FQQUtil.jsonLogin(back);
			if(map==null){
				logger.error("登录失败:"+back);
				return null;
			}
			if(map.get("psessionid")!=null){
				logger.info("正式登陆成功");
				loginModel.setPsessionid(map.get("psessionid"));
				if(cookies!=null){
					String cooStr="";
					for(String c:cookies.keySet()){
						cooStr+=c+"="+cookies.get(c)+";";
					}
//					PropertiesUtil.WriteProperties("cookie",cooStr);
				}
				//消息处理
				return new Message(loginModel,cookies,listener);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
}
