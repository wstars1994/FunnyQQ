package com.boomzz.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WStars
 *
 */
public class Config {
	
	/**
	 * clientId 暂时不变
	 */
	public static String PARAM_CLIENTID="53999199";
	
	/**
	 * 发送消息参数
	 */
	public static String PARAM_SENDMSG="{\"to\":#to#,\"content\":\"#content#\",\"face\":#face#,\"clientid\":"+PARAM_CLIENTID+",\"msg_id\":#msg_id#,\"psessionid\":\"#psessionid#\"}";

	/**
	 * 第二次登录请求参数
	 */
	public static String PARAM_LOGIN2="{\"ptwebqq\":\"#ptwebqq#\",\"clientid\":"+PARAM_CLIENTID+",\"psessionid\":\"\",\"status\":\"online\"}";

	/**
	 * 获取好友列表请求参数
	 */
	public static String PARAM_FRIENDS_LIST="{\"vfwebqq\":\"#vfwebqq#\",\"hash\":\"#hash#\"}";
	
	/**
	 * 获取最近好友列表请求参数
	 */
	public static String PARAM_RECENTFRIENDS_LIST="{\"vfwebqq\":\"vfwebqq\",\"clientid\":"+PARAM_CLIENTID+",\"psessionid\":\"#psessionid#\"}";
	
	/**
	 * 图片路径
	 */
	public static String FILE_PATH_QR="qr.png";

	/**
	 * 图片在本地生成
	 */
	public static boolean FILE_IMG_LOCAL=true;
	
	/**
	 * 日志路径
	 */
	public static String FILE_PATH_LOGS="logs/log.log";
	
	/**
	 * 获取二维码图片
	 */
	public static String URL_GET_QR="https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=";
	
	/**
	 *登录扫描验证 初次登陆
	 */
	public static String URL_GET_LOGIN_POLLING="https://ssl.ptlogin2.qq.com/ptqrlogin?ptqrtoken=#ptqrtoken#&webqq_type=10&remember_uin=1&login2qq=1&aid=501004106&u1=http://w.qq.com/proxy.html?login2qq=1&webqq_type=10&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=0-0-666&mibao_css=m_webqq&t=undefined&g=1&js_type=0&js_ver=10197&login_sig=&pt_randsalt=0";

	/**
	 * 获取VFWEBQQ参数
	 */
	public static String URL_GET_VFWEBQQ="http://s.web2.qq.com/api/getvfwebqq?ptwebqq=#ptwebqq#&clientid="+PARAM_CLIENTID+"&psessionid=&t=";
	
	/**
	 * 第二次登录 正式登陆
	 */
	public static String URL_POST_LONGIN2="http://d1.web2.qq.com/channel/login2";
	/**
	 * 获取个人信息
	 */
	public static String URL_GET_SELFINFO="http://s.web2.qq.com/api/get_self_info2?t=";
	
	/**
	 * 获取个人头像
	 */
	public static String URL_GET_SELFPIC="http://q.qlogo.cn/g?b=qq&nk=#uid#&s=100&t=1488264587514";
	
	/**
	 * 获取好友列表
	 */
	public static String URL_POST_FRIENDS="http://s.web2.qq.com/api/get_user_friends2";
	
	/**
	 * 获取群列表
	 */
	public static String URL_POST_GROUP="http://s.web2.qq.com/api/get_group_name_list_mask2";
	
	/**
	 * 获取讨论组列表
	 */
	public static String URL_GET_DISCUS="http://s.web2.qq.com/api/get_discus_list?clientid="+PARAM_CLIENTID+"&psessionid=#psessionid#&vfwebqq=#vfwebqq#&t=";

	/**
	 * 获取在线好友
	 */
	public static String URL_GET_ONLINEFRIENDS="http://d1.web2.qq.com/channel/get_online_buddies2?vfwebqq=#vfwebqq#&clientid="+PARAM_CLIENTID+"&psessionid=#psessionid#&t=";	
	
	/**
	 * 获取最近联系人
	 */
	public static String URL_POST_RECENTRIENDS="http://d1.web2.qq.com/channel/get_recent_list2";
	
	/**
	 * 获取新消息  注意:此处为长连接
	 */
	public static String URL_POST_NEWMESSAGE="https://d1.web2.qq.com/channel/poll2";
	
	/**
	 * 发送消息
	 */
	public static String URL_POST_SENDMESSAGE="https://d1.web2.qq.com/channel/poll2";
	
}
