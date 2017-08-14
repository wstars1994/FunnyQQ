package com.boomzz.core.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.IQQListener;
import com.boomzz.core.cache.Cache;
import com.boomzz.core.message.model.MMsgSend;
import com.boomzz.core.model.MBase;
import com.boomzz.core.model.MDiscus;
import com.boomzz.core.model.MFriends;
import com.boomzz.core.model.MGroup;
import com.boomzz.core.model.MInfo;
import com.boomzz.core.model.MLogin;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClient;

/**
 * 消息类 接收消息 发送消息 获取信息
 * @author WStars
 */
public final class Message{
	
	public static MLogin loginModel;
	
	protected Map<String, String> cookies;
	
	public Message(){}
	
	public Message(MLogin loginModel,Map<String, String> cookies,IQQListener listener){
		Message.loginModel=loginModel;
		this.cookies=cookies;
		getSelfInfo();
		getFrientList();
		getGroupList();
		getDiscusList();
		new Thread(new TMsgAccept(this,listener)).start();
//		String url="https://user.qzone.qq.com/"+Message.loginModel.getUin();
//		String string = HttpClient.get(url, cookies);
//		System.out.println(string);
		//定时更新在线好友
//		new TFriendsOnline(this).start();
	}
	
	public void sendMessage(MMsgSend m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"to",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		HttpClient.postHttps(Config.URL_POST_SENDMESSAGE, params,cookies);
	}
	
	public void sendGroupMessage(MMsgSend m){
		String param = String.format(Config.PARAM_MESSAGE_SEND,"group_uin",m.getUin(),m.getContent(),loginModel.getPsessionid());
		Map<String, String> params=new HashMap<>();
		params.put("r", param);
		HttpClient.postHttps(Config.URL_POST_SENDMESSAGE_GROUP, params,cookies);
	}
	
	public void sendDiscusMessage(MMsgSend m){
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
	
	private void updateOnlineFriends(String json,Map<String,MFriends> friendsModel){
		List<String> uinList = FQQUtil.jsonOnlineFriendsList(json);
		for(String uin:uinList){
			friendsModel.get(uin).setOnline(true);
		}
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
		return getGroupListOnline();
	}
	public Map<String, MGroup> getGroupListOnline(){
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("hash", getHash());
		String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClient.post(Config.URL_POST_GROUP,params,cookies);
		Map<String, MGroup> jsonGroupList = FQQUtil.jsonGroupList(json);
		for(String gcode : jsonGroupList.keySet()){
			Map<String,String> param=new HashMap<>();
			param.put("gcode", gcode);
			param.put("vfwebqq", loginModel.getVfwebqq());
			pString=FQQUtil.replace(Config.URL_GET_GROUP_MEMBER, param);
			param.clear();
			param.put("r", pString);
			String jsonMember=HttpClient.get(pString+DateTimeUtil.getTimestamp(),cookies);
			Map<String, MBase> jsonGroupMemberList = FQQUtil.jsonGroupMemberList(jsonMember);
			if(jsonGroupMemberList.size()==0){
				param.put("gcode", jsonGroupList.get(gcode).getCode());
				param.put("vfwebqq", loginModel.getVfwebqq());
				pString=FQQUtil.replace(Config.URL_GET_GROUP_MEMBER, param);
				param.clear();
				param.put("r", pString);
				jsonMember=HttpClient.get(pString+DateTimeUtil.getTimestamp(),cookies);
				jsonGroupMemberList = FQQUtil.jsonGroupMemberList(jsonMember);
			}
			jsonGroupList.get(gcode).setMember(jsonGroupMemberList);
		}
		Cache.putCache(Config.CACHE_KEY_GROUP, jsonGroupList);
		return jsonGroupList;
	}
	public Map<String, MDiscus> getDiscusList() {
		if(Cache.getCache(Config.CACHE_KEY_DISCUS)!=null)
			return (Map<String, MDiscus>) Cache.getCache(Config.CACHE_KEY_DISCUS);
		return getDiscusListOnline();
	}
	public Map<String, MDiscus> getDiscusListOnline(){
		Map<String, String> map=new HashMap<>();
		map.put("psessionid", loginModel.getPsessionid());
		map.put("vfwebqq", loginModel.getVfwebqq());
		String url=FQQUtil.replace(Config.URL_GET_DISCUS+Math.random(), map);
		String back=HttpClient.get(url,cookies);
		Map<String, MDiscus> jsonDiscusList = FQQUtil.jsonDiscusList(back);
		Cache.putCache(Config.CACHE_KEY_DISCUS, jsonDiscusList);
		return jsonDiscusList;
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
