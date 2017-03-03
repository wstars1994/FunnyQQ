package com.boomzz.core;

import java.util.HashMap;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 13bda06a56e3927ce725a8acca3656fa7a763591
import java.util.Map;

import com.boomzz.logs.FQQLogs;
import com.boomzz.model.DiscusModel;
import com.boomzz.model.FriendsModel;
import com.boomzz.model.GroupModel;
import com.boomzz.model.LoginModel;
import com.boomzz.model.UserModel;

/**
 * @author WStars
 *
 */
public interface IFunnyQQBase {
	
	/**
	 * 全局Cookies
	 */
	public static Map<String, String> cookies=new HashMap<String, String>(); //
	
	/**
	 * 全局个人登录信息
	 */
	public static LoginModel loginModel=new LoginModel(); //
	
	
	public static FQQLogs logs=new FQQLogs(Config.FILE_PATH_LOGS);
	
	/**获取必要参数 Hash
	 * @return
	 */
	public String getHash();
	/**
	 * 获取个人信息 
	 * @return json
	 */
	public UserModel getSelfInfo();
	
	/**
	 * 获取所有好友 
	 * @return json
	 */
	public List<FriendsModel> getFrientList();
	
	/**
	 * 获取在线好友 
	 * @return json
	 */
	public List<FriendsModel> getOnlineFrientList();
	
	/**
	 * 获取最近联系人 
	 * @return json
	 */
	public List<FriendsModel> getRecentFrientList();
	
	/**
	 * 获取群
	 * @return json
	 */
	public List<GroupModel> getGroupList();
	
	/**
	 * 获取讨论组 (暂时拿不到讨论组)
	 * @return json
	 */
	public List<DiscusModel> getDiscusList();
	
	/**
	 * 发送消息
	 * @return json
	 */
	public String sendMessage(String msg,String user);
	
}
