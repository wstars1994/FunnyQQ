package com.boomzz.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.boomzz.core.cache.Cache;
import com.boomzz.core.model.DiscusModel;
import com.boomzz.core.model.FriendsModel;
import com.boomzz.core.model.GroupModel;
import com.boomzz.core.model.InfoModel;
import com.boomzz.core.model.LoginModel;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FQQUtil;
import com.boomzz.util.HttpClientUtil;

/**
 * @author WStars
 *
 */
public abstract class FQQ{

	//个人登录信息
	public static LoginModel loginModel = new LoginModel();
	//全局Cookie
	public static Map<String, String> cookies = new HashMap<>();
	
	public void login(){
		try {
			Cache.getPersist();
			//第一次登陆 表现为登录方式 cookie获取完毕 相应值已经获取并设置
			if(login_1()){
				//获取必须的Vfwebqq
				if(loginModel.getPtwebqq()==null){
					System.out.println("必要参数缺失");
					return;
				}
				String back=HttpClientUtil.get(FQQUtil.replace(com.boomzz.core.Config.URL_GET_VFWEBQQ+DateTimeUtil.getTimestamp(), "ptwebqq",loginModel.getPtwebqq()), cookies);
				loginModel.setVfwebqq(FQQUtil.jsonVfwebqq(back));
				//第二次登录验证
				Map<String, String> params=new HashMap<>();
				params.put("r", FQQUtil.replace(Config.PARAM_LOGIN2, "ptwebqq",loginModel.getPtwebqq()));
				back=HttpClientUtil.post(Config.URL_POST_LONGIN2, params, cookies);
				Map<String, String> map=FQQUtil.jsonLogin(back);
				if(map.get("psessionid")!=null){
					System.out.println("正式登陆成功");
					loginModel.setPsessionid(map.get("psessionid"));
					loginSuccess();
					Cache.putCache("cookie", cookies);
					Cache.persist();
				}
				while(true)
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
						frientList = getOnlineFrientList();
						for(FriendsModel model : frientList){
							System.out.println(model.toString());
						}
						break;
					case "4":
						getRecentFrientList();
						break;
					case "5":
						List<GroupModel> groupList = getGroupList();
						for(GroupModel m:groupList)
							System.out.println(m.toString());
						break;
					case "6":
						List<DiscusModel> discusList = getDiscusList();
						for(DiscusModel m:discusList)
							System.out.println(m.toString());
						break;
					case "7":
						System.out.print(">> 退出成功");
						return;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("系统错误");
		}
	}
	public abstract boolean login_1();
	public abstract void loginSuccess();
	
	protected InfoModel getSelfInfo() {
		InfoModel userModel;
		if(Cache.getCache(Config.CACHE_KEY_MYSELF)!=null)
			userModel=(InfoModel) Cache.getCache(Config.CACHE_KEY_MYSELF);
		else{
			String back=HttpClientUtil.get(Config.URL_GET_SELFINFO+DateTimeUtil.getTimestamp(),cookies);
			userModel=FQQUtil.jsonInfo(back);
			Cache.putCache(Config.CACHE_KEY_MYSELF, userModel);
		}
		return userModel;
	}

	protected List<FriendsModel> getFrientList() {
		List<FriendsModel> friendsModel;
		if(Cache.getCache(Config.CACHE_KEY_ALLFRIENDS)!=null)
			friendsModel=(List<FriendsModel>) Cache.getCache(Config.CACHE_KEY_ALLFRIENDS);
		else{
			Map<String,String> params=new HashMap<>();
			params.put("vfwebqq", loginModel.getVfwebqq());
			params.put("hash", getHash());
			String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
			params.clear();
			params.put("r", pString);
			String json=HttpClientUtil.post(Config.URL_POST_FRIENDS,params,cookies);
			friendsModel=FQQUtil.jsonFriendsList(json);
			Cache.putCache(Config.CACHE_KEY_ALLFRIENDS, friendsModel);
		}
		upOnlineFrientList(friendsModel);
		return friendsModel;
	}
	protected List<FriendsModel> getOnlineFrientList(){
		List<FriendsModel> friendsModel = new ArrayList<>();
		for(FriendsModel model : getFrientList()){
			if(model.getOnline())
				friendsModel.add(model);
		}
		return friendsModel;
	}
	private void upOnlineFrientList(List<FriendsModel> friendsModel) {
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("psessionid", loginModel.getPsessionid());
		String url=FQQUtil.replace(Config.URL_GET_ONLINEFRIENDS, params);
		String json=HttpClientUtil.get(url+DateTimeUtil.getTimestamp(),cookies);
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
		String json=HttpClientUtil.post(Config.URL_POST_RECENTRIENDS,params,cookies);
		System.out.println(json);
		return friendsModel;
	}

	public List<GroupModel> getGroupList() {
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("hash", getHash()+"");
		String pString=FQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClientUtil.post(Config.URL_POST_GROUP,params,cookies);
		List<GroupModel> gList = FQQUtil.jsonGroupList(json);
		return gList;
	}

	protected List<DiscusModel> getDiscusList() {
		List<DiscusModel> discusModels;
		if(Cache.getCache(Config.CACHE_KEY_DISCUS)!=null)
			discusModels=(List<DiscusModel>) Cache.getCache(Config.CACHE_KEY_DISCUS);
		else{
			Map<String, String> map=new HashMap<>();
			map.put("psessionid", loginModel.getPsessionid());
			map.put("vfwebqq", loginModel.getVfwebqq());
			String url=FQQUtil.replace(Config.URL_GET_DISCUS+Math.random(), map);
			String back=HttpClientUtil.get(url,cookies);
			discusModels = FQQUtil.jsonDiscusList(back);
		}
		return discusModels;
	}
	
	private void updateOnlineFriends(String json,List<FriendsModel> friendsModel){
		List<String> uinList = FQQUtil.jsonOnlineFriendsList(json);
		for(FriendsModel f:friendsModel){
			for(String uin:uinList){
				if(f.getUin().equals(uin)){
					f.setOnline(true);
				}
			}
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
