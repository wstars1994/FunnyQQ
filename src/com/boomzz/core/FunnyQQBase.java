package com.boomzz.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public List<FriendsModel> getFrientList() {
		List<FriendsModel> friendsModel=new ArrayList<FriendsModel>();
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("hash", getHash()+"");
		String pString=FunnyQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClientUtil.post(Config.URL_POST_FRIENDS,params,cookies);
		return friendsModel;
	}

	@Override
	public List<FriendsModel> getOnlineFrientList() {
		List<FriendsModel> friendsModel=new ArrayList<>();
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("psessionid", loginModel.getPsessionid());
		String url=FunnyQQUtil.replace(Config.URL_GET_ONLINEFRIENDS, params);
		String json=HttpClientUtil.get(url+DateTimeUtil.getTimestamp(),cookies);
		return friendsModel;
	}

	@Override
	public List<FriendsModel> getRecentFrientList() {
		List<FriendsModel> friendsModel=new ArrayList<>();
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("psessionid", loginModel.getPsessionid());
		String url=FunnyQQUtil.replace(Config.PARAM_RECENTFRIENDS_LIST, params);
		params.clear();
		params.put("r", url);
		String json=HttpClientUtil.post(Config.URL_POST_RECENTRIENDS,params,cookies);
		System.out.println(json);
		return friendsModel;
	}

	@Override
	public List<GroupModel> getGroupList() {
		List<GroupModel> groupModel=new ArrayList<>();
		Map<String,String> params=new HashMap<>();
		params.put("vfwebqq", loginModel.getVfwebqq());
		params.put("hash", getHash()+"");
		String pString=FunnyQQUtil.replace(Config.PARAM_FRIENDS_LIST, params);
		params.clear();
		params.put("r", pString);
		String json=HttpClientUtil.post(Config.URL_POST_GROUP,params,cookies);
		System.out.println(json);
		return groupModel;
	}

	@Deprecated
	@Override
	public List<DiscusModel> getDiscusList() {
		List<DiscusModel> discusModel=new ArrayList<>();
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

	@Override
	public String getHash() {
		int uin=Integer.parseInt(loginModel.getUid());
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
//
		for (int i=0;i<result.length;i++){
			buf += (hex[(result[i]>>4) & 0xF]);
			buf += (hex[result[i] & 0xF]);
		}
		return buf;
	}
}
