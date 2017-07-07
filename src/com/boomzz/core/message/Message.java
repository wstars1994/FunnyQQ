package com.boomzz.core.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.cache.Cache;
import com.boomzz.core.cache.thread.TFriendsOnline;
import com.boomzz.core.message.model.MMSGSend;
import com.boomzz.core.model.MDiscus;
import com.boomzz.core.model.MFriends;
import com.boomzz.core.model.MGroup;
import com.boomzz.core.model.MInfo;
import com.boomzz.core.model.MLogin;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

public class Message{
	
	
	protected MLogin loginModel;
	
	protected Map<String, String> cookies;
	
	public Message(){}
	
	public Message(MLogin loginModel,Map<String, String> cookies){
		this.loginModel=loginModel;
		this.cookies=cookies;
		getSelfInfo();
		getFrientList();
		getGroupList();
		getDiscusList();
		//接收消息
		new Thread(new TMSGAccept()).start();
		//定时更新在线好友
		new TFriendsOnline(this).start();
	}
	
	public void sendMessage(MMSGSend m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"to",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		HttpClient.postHttps(Config.URL_POST_SENDMESSAGE, params,cookies);
	}
	
	public void sendGroupMessage(MMSGSend m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"group_uin",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		HttpClient.postHttps(Config.URL_POST_SENDMESSAGE_GROUP, params,cookies);
	}
	
	public void sendDiscusMessage(MMSGSend m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"did",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		HttpClient.postHttps(Config.URL_POST_SENDMESSAGE_DISCUS, params,cookies);
	}
	
	public MInfo getSelfInfo() {
		if(Cache.getCache(Config.CACHE_KEY_MYSELF)!=null)
			return (MInfo) Cache.getCache(Config.CACHE_KEY_MYSELF);
		else{
			MInfo userModel=getSelfInfoOnline();
			Cache.putCache(Config.CACHE_KEY_MYSELF, userModel);
			return userModel;
		}
	}
	public MInfo getSelfInfoOnline(){
		String back=HttpClient.get(Config.URL_GET_SELFINFO+DateTimeUtil.getTimestamp(),cookies);
		MInfo userModel=FQQUtil.jsonInfo(back);
		return userModel;
	}
	
	public Map<String,MFriends> getFrientList() {
		Map<String,MFriends> friendsModel;
		if(Cache.getCache(Config.CACHE_KEY_ALLFRIENDS)!=null)
			friendsModel=(Map<String,MFriends>) Cache.getCache(Config.CACHE_KEY_ALLFRIENDS);
		else{
			friendsModel=getFrientListOnline();
			Cache.putCache(Config.CACHE_KEY_ALLFRIENDS, friendsModel);
		}
		upOnlineFrientList(friendsModel);
		return friendsModel;
	}
	
	public Map<String,MFriends> getFrientListOnline() {
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("hash", getHash());
		String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClient.post(Config.URL_POST_FRIENDS,params,cookies);
		return FQQUtil.jsonFriendsList(json);
	}
	
	public List<MFriends> getOnlineFrientList(){
		List<MFriends> friendsModel = new ArrayList<>();
		Map<String, MFriends> map = getFrientList();
		for(String uin : map.keySet()){
			if(map.get(uin).getOnline())
				friendsModel.add(map.get(uin));
		}
		return friendsModel;
	}
	private void upOnlineFrientList(Map<String,MFriends> friendsModel) {
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("psessionid", loginModel.getPsessionid());
		String url=FQQUtil.replace(Config.URL_GET_ONLINEFRIENDS, params);
		String json=HttpClient.get(url+DateTimeUtil.getTimestamp(),cookies);
		updateOnlineFriends(json,friendsModel);
	}

	public List<MFriends> getRecentFrientList() {
		List<MFriends> friendsModel=new ArrayList<>();
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

	public Map<String, MGroup> getGroupList() {
		if(Cache.getCache(Config.CACHE_KEY_GROUP)!=null){
			return (Map<String, MGroup>) Cache.getCache(Config.CACHE_KEY_GROUP);
		}
		Map<String, MGroup> jsonGroupList = getGroupListOnline();
		Cache.putCache(Config.CACHE_KEY_GROUP, jsonGroupList);
		return jsonGroupList;
	}
	public Map<String, MGroup> getGroupListOnline(){
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("hash", getHash());
		String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClient.post(Config.URL_POST_GROUP,params,cookies);
		return FQQUtil.jsonGroupList(json);
	}
	public Map<String, MDiscus> getDiscusList() {
		if(Cache.getCache(Config.CACHE_KEY_DISCUS)!=null)
			return (Map<String, MDiscus>) Cache.getCache(Config.CACHE_KEY_DISCUS);
		else{
			Map<String, MDiscus> discusModels = getDiscusListOnline();
			Cache.putCache(Config.CACHE_KEY_DISCUS, discusModels);
			return discusModels;
		}
	}
	public Map<String, MDiscus> getDiscusListOnline(){
		Map<String, String> map=new HashMap<>();
		map.put("psessionid", loginModel.getPsessionid());
		map.put("vfwebqq", loginModel.getVfwebqq());
		String url=FQQUtil.replace(Config.URL_GET_DISCUS+Math.random(), map);
		String back=HttpClient.get(url,cookies);
		return FQQUtil.jsonDiscusList(back);
	}
	private void updateOnlineFriends(String json,Map<String,MFriends> friendsModel){
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
