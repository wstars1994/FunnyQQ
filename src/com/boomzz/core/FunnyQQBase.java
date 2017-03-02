package com.boomzz.core;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.model.DiscusModel;
import com.boomzz.model.FriendsModel;
import com.boomzz.model.GroupModel;
import com.boomzz.model.UserModel;
import com.boomzz.util.DateTimeUtil;
import com.boomzz.util.FunnyQQUtil;
import com.boomzz.util.HttpClientUtil;

/**
 * @author WStars
 *
 */
public class FunnyQQBase implements IFunnyQQBase{

	@Override
	public UserModel getSelfInfo() {
		//{"retcode":0,"result":{"birthday":{"month":6,"year":1994,"day":15},"face":558,"phone":"110","occupation":"高中生","allow":3,"college":"赤城县第一中学","uin":545640807,"blood":3,"constel":5,"lnick":"","vfwebqq":"bdf14d67b30317082791abe43eec8d12c863fdce52f2d62234ea6b1a5c60c676b33654de9d6adc23","homepage":"http://user.qzone.qq.com/545640807/infocenter","vip_info":0,"city":"张家口","country":"中国","personal":"","shengxiao":11,"nick":"战略忽悠局副局座同学","email":"545640807@qq.com","province":"河北","account":545640807,"gender":"male","mobile":"150********"}}
		UserModel userModel=new UserModel();
		
		String back=HttpClientUtil.get(Config.URL_GET_SELFINFO+DateTimeUtil.getTimestamp(),cookies);
		System.out.println("selfInfo : "+back);
		
		return userModel;
	}

	@Override
	public FriendsModel getFrientList() {
		Map<String,String> params=new HashMap<>();
		
		FriendsModel friendsModel=new FriendsModel();
		HttpClientUtil.post(Config.URL_POST_FRIENDS,params,cookies);
		
		return friendsModel;
	}

	@Override
	public FriendsModel getOnlineFrientList() {
		FriendsModel friendsModel=new FriendsModel();
		
		HttpClientUtil.get(Config.URL_GET_ONLINEFRIENDS+Math.random(),cookies);
		
		return friendsModel;
	}

	@Override
	public FriendsModel getRecentFrientList() {
		FriendsModel friendsModel=new FriendsModel();
		Map<String,String> params=new HashMap<>();
		HttpClientUtil.post(Config.URL_POST_RECENTRIENDS,params,cookies);
		return friendsModel;
	}

	@Override
	public GroupModel getGroupList() {
		GroupModel groupModel=new GroupModel();
		
		Map<String,String> params=new HashMap<>();
		
		HttpClientUtil.post(Config.URL_POST_GROUP,params,cookies);
		
		return groupModel;
	}

	@Deprecated
	@Override
	public DiscusModel getDiscusList() {
		DiscusModel discusModel=new DiscusModel();
		Map<String, String> map=new HashMap<>();
		map.put("psessionid", loginModel.getPsessionid());
		map.put("vfwebqq", loginModel.getVfwebqq());
		String url=FunnyQQUtil.replace(Config.URL_GET_DISCUS+Math.random(), map);
		String back=HttpClientUtil.get(url,cookies);
		System.out.println("discus : "+back);
		return discusModel;
	}

	@Override
	public String sendMessage(String msg, String user) {
		
		return null;
	}

}
