package com.boomzz.core.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.cache.Cache;
import com.boomzz.core.login.AbstractLogin;
import com.boomzz.core.model.DiscusModel;
import com.boomzz.core.model.FriendsModel;
import com.boomzz.core.model.GroupModel;
import com.boomzz.core.model.InfoModel;
import com.boomzz.core.model.LoginModel;
import com.boomzz.core.model.MessageModel;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

public class Message extends Thread{
	
	private AbstractLogin fqq;
	
	private LoginModel loginModel;
	
	private Map<String, String> cookies;
	
	public Message(AbstractLogin fqq,LoginModel loginModel,Map<String, String> cookies){
		this.fqq = fqq;
		this.loginModel=loginModel;
		this.cookies=cookies;
		getSelfInfo();
		getFrientList();
		getGroupList();
		getDiscusList();
	}
	
	@Override
	public void run() {
		while(true){
			Map<String, String> params=new HashMap<>();
			params.put("ptwebqq", loginModel.getPtwebqq());
			params.put("psessionid", loginModel.getPsessionid());
			String url=FQQUtil.replace(Config.PARAM_MESSAGE_POLL, params);
			params.clear();
			params.put("r",url);
			String back=HttpClient.postHttps(Config.URL_POST_NEWMESSAGE, params,cookies);
			System.out.println(back);
			newMessage(null);
		}
	}
	
	private void newMessage(MessageModel model){
		
	}
	
	public void sendMessage(MessageSendModel m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"to",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		String back=HttpClient.postHttps(Config.URL_POST_SENDMESSAGE, params,cookies);
		System.out.println(back);
	}
	
	public void sendGroupMessage(MessageSendModel m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"group_uin",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		String back=HttpClient.postHttps(Config.URL_POST_SENDMESSAGE_GROUP, params,cookies);
		System.out.println(back);
	}
	
	public void sendDiscusMessage(MessageSendModel m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"did",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		String back=HttpClient.postHttps(Config.URL_POST_SENDMESSAGE_DISCUS, params,cookies);
		System.out.println(back);
	}
	
	public InfoModel getSelfInfo() {
		if(Cache.getCache(Config.CACHE_KEY_MYSELF)!=null)
			return (InfoModel) Cache.getCache(Config.CACHE_KEY_MYSELF);
		else{
			String back=HttpClient.get(Config.URL_GET_SELFINFO+DateTimeUtil.getTimestamp(),cookies);
			InfoModel userModel=FQQUtil.jsonInfo(back);
			Cache.putCache(Config.CACHE_KEY_MYSELF, userModel);
			return userModel;
		}
	}

	public Map<String,FriendsModel> getFrientList() {
		Map<String,FriendsModel> friendsModel;
		if(Cache.getCache(Config.CACHE_KEY_ALLFRIENDS)!=null)
			friendsModel=(Map<String,FriendsModel>) Cache.getCache(Config.CACHE_KEY_ALLFRIENDS);
		else{
			Map<String,String> params=new HashMap<>();
			params.put("vfwebqq", loginModel.getVfwebqq());
			params.put("hash", getHash());
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
	public List<FriendsModel> getOnlineFrientList(){
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

	public List<FriendsModel> getRecentFrientList() {
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
		params.put("hash", getHash());
		String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClient.post(Config.URL_POST_GROUP,params,cookies);
		List<GroupModel> jsonGroupList = FQQUtil.jsonGroupList(json);
		Cache.putCache(Config.CACHE_KEY_GROUP, jsonGroupList);
		return jsonGroupList;
	}

	public List<DiscusModel> getDiscusList() {
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
	private String getHash() {
		int uin=Integer.parseInt(loginModel.getId());
		String ptvfwebqq=loginModel.getPtwebqq();
		int ptb[]=new int[4];
		for (int i=0;i<ptvfwebqq.length();i++){
            int ptbIndex = i%4;
            ptb[ptbIndex] ^= ptvfwebqq.charAt(i);
        }
		String salt[]={"EC", "OK"};
		int uinByte[]={(((uin >> 24) & 0xFF) ^ salt[0].charAt(0)),(((uin >> 16) & 0xFF) ^ salt[0].charAt(1)),(((uin >> 8) & 0xFF) ^ salt[1].charAt(0)),((uin & 0xFF) ^ salt[1].charAt(1))};
		int result[] = new int[8];
		for (int i=0;i<8;i++){
			if (i%2 == 0)
				result[i] = ptb[i>>1];
			else
				result[i] = uinByte[i>>1];
        }
		char hex[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		String buf = "";
		for (int i=0;i<result.length;i++){
			buf += (hex[(result[i]>>4) & 0xF]);
			buf += (hex[result[i] & 0xF]);
		}
		return buf;
	}
}
