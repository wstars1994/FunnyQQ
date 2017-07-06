package com.boomzz.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boomzz.core.cache.Cache;
import com.boomzz.core.message.Message;
import com.boomzz.core.model.DiscusModel;
import com.boomzz.core.model.FriendsModel;
import com.boomzz.core.model.GroupModel;
import com.boomzz.core.model.InfoModel;
import com.boomzz.core.model.LoginModel;
import com.boomzz.core.model.MessageModel;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

/**
 * @author WStars
 *
 */
public abstract class FQQ{

	private final Logger logger = LogManager.getLogger();
	//个人登录信息
	public static LoginModel loginModel = new LoginModel();
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
					getSelfInfo();
					getFrientList();
					getGroupList();
					getDiscusList();
					//开始接收消息
					Message message = new Message(this);
					message.start();
					return message;
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	protected InfoModel getSelfInfo() {
		if(Cache.getCache(Config.CACHE_KEY_MYSELF)!=null)
			return (InfoModel) Cache.getCache(Config.CACHE_KEY_MYSELF);
		else{
			String back=HttpClient.get(Config.URL_GET_SELFINFO+DateTimeUtil.getTimestamp(),cookies);
			InfoModel userModel=FQQUtil.jsonInfo(back);
			Cache.putCache(Config.CACHE_KEY_MYSELF, userModel);
			return userModel;
		}
	}

	protected Map<String,FriendsModel> getFrientList() {
		Map<String,FriendsModel> friendsModel;
		if(Cache.getCache(Config.CACHE_KEY_ALLFRIENDS)!=null)
			friendsModel=(Map<String,FriendsModel>) Cache.getCache(Config.CACHE_KEY_ALLFRIENDS);
		else{
			Map<String,String> params=new HashMap<>();
			params.put("vfwebqq", loginModel.getVfwebqq());
			params.put("hash", FQQUtil.getHash());
			String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
			params.clear();
			params.put("r", pString);
			String json=HttpClient.post(Config.URL_POST_FRIENDS,params,cookies);
			friendsModel=FQQUtil.jsonFriendsList(json);
			Cache.putCache(Config.CACHE_KEY_ALLFRIENDS, friendsModel);
		}
		upOnlineFrientList(friendsModel);
		return friendsModel;
	}
	protected List<FriendsModel> getOnlineFrientList(){
		List<FriendsModel> friendsModel = new ArrayList<>();
		Map<String, FriendsModel> map = getFrientList();
		for(String uin : map.keySet()){
			if(map.get(uin).getOnline())
				friendsModel.add(map.get(uin));
		}
		return friendsModel;
	}
	private void upOnlineFrientList(Map<String,FriendsModel> friendsModel) {
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("psessionid", loginModel.getPsessionid());
		String url=FQQUtil.replace(Config.URL_GET_ONLINEFRIENDS, params);
		String json=HttpClient.get(url+DateTimeUtil.getTimestamp(),cookies);
		updateOnlineFriends(json,friendsModel);
	}

	protected List<FriendsModel> getRecentFrientList() {
		List<FriendsModel> friendsModel=new ArrayList<>();
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("psessionid", loginModel.getPsessionid());
		String url=FQQUtil.replace(Config.PARAM_RECENTFRIENDS_LIST, params);
		params.clear();
		params.put("r", url);
		String json=HttpClient.post(Config.URL_POST_RECENTRIENDS,params,cookies);
		System.out.println(json);
		return friendsModel;
	}

	public List<GroupModel> getGroupList() {
		if(Cache.getCache(Config.CACHE_KEY_GROUP)!=null){
			return (List<GroupModel>) Cache.getCache(Config.CACHE_KEY_GROUP);
		}
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("hash", FQQUtil.getHash());
		String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClient.post(Config.URL_POST_GROUP,params,cookies);
		List<GroupModel> jsonGroupList = FQQUtil.jsonGroupList(json);
		Cache.putCache(Config.CACHE_KEY_GROUP, jsonGroupList);
		return jsonGroupList;
	}

	protected List<DiscusModel> getDiscusList() {
		
		if(Cache.getCache(Config.CACHE_KEY_DISCUS)!=null)
			return (List<DiscusModel>) Cache.getCache(Config.CACHE_KEY_DISCUS);
		else{
			Map<String, String> map=new HashMap<>();
			map.put("psessionid", loginModel.getPsessionid());
			map.put("vfwebqq", loginModel.getVfwebqq());
			String url=FQQUtil.replace(Config.URL_GET_DISCUS+Math.random(), map);
			String back=HttpClient.get(url,cookies);
			List<DiscusModel> discusModels = FQQUtil.jsonDiscusList(back);
			Cache.putCache(Config.CACHE_KEY_DISCUS, discusModels);
			return discusModels;
		}
	}
	private void updateOnlineFriends(String json,Map<String,FriendsModel> friendsModel){
		List<String> uinList = FQQUtil.jsonOnlineFriendsList(json);
		for(String uin:uinList){
			friendsModel.get(uin).setOnline(true);
		}
	}
}
