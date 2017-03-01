package com.boomzz.core;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.model.DiscusModel;
import com.boomzz.model.FriendsModel;
import com.boomzz.model.GroupModel;
import com.boomzz.model.UserModel;
import com.boomzz.util.HttpClientUtil;

/**
 * @author WStars
 *
 */
public class FunnyQQBase implements IFunnyQQBase{

	@Override
	public UserModel getSelfInfo() {
		UserModel userModel=new UserModel();
		
		HttpClientUtil.get(Constant.URL_GET_SELFINFO+Math.random(),cookies);
		
		return userModel;
	}

	@Override
	public FriendsModel getFrientList() {
		Map<String,String> params=new HashMap<>();
		
		FriendsModel friendsModel=new FriendsModel();
		HttpClientUtil.post(Constant.URL_POST_FRIENDS,params,cookies);
		
		return friendsModel;
	}

	@Override
	public FriendsModel getOnlineFrientList() {
		FriendsModel friendsModel=new FriendsModel();
		
		HttpClientUtil.get(Constant.URL_GET_ONLINEFRIENDS+Math.random(),cookies);
		
		return friendsModel;
	}

	@Override
	public FriendsModel getRecentFrientList() {
		FriendsModel friendsModel=new FriendsModel();
		Map<String,String> params=new HashMap<>();
		HttpClientUtil.post(Constant.URL_POST_RECENTRIENDS,params,cookies);
		return friendsModel;
	}

	@Override
	public GroupModel getGroupList() {
		GroupModel groupModel=new GroupModel();
		
		Map<String,String> params=new HashMap<>();
		
		HttpClientUtil.post(Constant.URL_POST_GROUP,params,cookies);
		
		return groupModel;
	}

	@Override
	public DiscusModel getDiscusList() {
		DiscusModel discusModel=new DiscusModel();
		
		HttpClientUtil.get(Constant.URL_GET_DISCUS+Math.random(),cookies);
		
		return discusModel;
	}

	@Override
	public String sendMessage(String msg, String user) {
		
		return null;
	}

}
