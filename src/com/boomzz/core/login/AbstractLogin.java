package com.boomzz.core.login;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.boomzz.core.Config;
import com.boomzz.core.message.Message;
import com.boomzz.core.model.MLogin;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

/**
 * @author WStars
 *
 */
public abstract class AbstractLogin{

	private final Logger logger = LogManager.getLogger();
	//个人登录信息
	public MLogin loginModel = new MLogin();
	//全局Cookie
	public static Map<String, String> cookies = new HashMap<>();
	
	public abstract boolean login_1();

	public Message login(){
		try {
			//第一次登陆 表现为登录方式 cookie获取完毕 相应值已经获取并设置
			if(login_1()){
				String back=HttpClient.get(FQQUtil.replace(com.boomzz.core.Config.URL_GET_VFWEBQQ+DateTimeUtil.getTimestamp(), "ptwebqq",loginModel.getPtwebqq()), cookies);
				loginModel.setVfwebqq(FQQUtil.jsonVfwebqq(back));
				//第二次登录验证
				Map<String, String> params=new HashMap<>();
				params.put("r", FQQUtil.replace(Config.PARAM_LOGIN2, "ptwebqq",loginModel.getPtwebqq()));
				back=HttpClient.post(Config.URL_POST_LONGIN2, params, cookies);
				Map<String, String> map=FQQUtil.jsonLogin(back);
				if(map.get("psessionid")!=null){
					logger.info("正式登陆成功");
					loginModel.setPsessionid(map.get("psessionid"));
					//开始接收消息
					Message message = new Message(this,loginModel,cookies);
					message.start();
					return message;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
}
