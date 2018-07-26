package com.boomzz.core;

/**
 * @author WStars
 *
 */
public class Config {
	
	
	
	/**
	 * 是否
	 */
	public static boolean AUTO_LOGIN=true;
	/**
	 * 是否开启缓存
	 */
	public static boolean CACHE=true;
	public static String CACHE_KEY_ALLFRIENDS="ALLFRIENDS";
	public static String CACHE_KEY_MYSELF="MYSELF";
	public static String CACHE_KEY_CATEGORIES="CATEGORIES";
	public static String CACHE_KEY_DISCUS="DISCUS";
	public static String CACHE_KEY_GROUP="GROUP";
	/**
	 * clientId 暂时不变
	 */
	public static String PARAM_CLIENTID="53999199";
	

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
	 * 消息接收
	 */
	public static String PARAM_MESSAGE_POLL="{\"ptwebqq\":\"#ptwebqq#\",\"clientid\":53999199,\"psessionid\":\"#psessionid#\",\"key\":\"\"}";
	
	/**
	 * 消息发送
	 */
	public static String PARAM_MESSAGE_SEND="{\"%s\": %s,\"content\": \"[\\\"%s\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\": 558,\"clientid\": 53999199,\"msg_id\": 53770009,\"psessionid\": \"%s\"}";
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
	public static String URL_GET_LOGIN_POLLING="https://ssl.ptlogin2.qq.com/ptqrlogin?ptqrtoken=#ptqrtoken#&webqq_type=10&remember_uin=1&login2qq=1&aid=501004106&u1=http://w.qq.com/proxy.html?login2qq=1&webqq_type=10&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=0-0-9489&mibao_css=m_webqq&t=undefined&g=1&js_type=0&js_ver=10224&login_sig=&pt_randsalt=0";

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
	 * 发送消息 个人
	 */
	public static String URL_POST_SENDMESSAGE="https://d1.web2.qq.com/channel/send_buddy_msg2";
	/**
	 * 发送消息 讨论组
	 */
	public static String URL_POST_SENDMESSAGE_DISCUS="https://d1.web2.qq.com/channel/send_discu_msg2";
	/**
	 * 发送消息 群组
	 */
	public static String URL_POST_SENDMESSAGE_GROUP="https://d1.web2.qq.com/channel/send_qun_msg2";
	/**
	 * 获取群成员信息
	 */
	public static String URL_GET_GROUP_MEMBER="http://s.web2.qq.com/api/get_group_info_ext2?gcode=#gcode#&vfwebqq=#vfwebqq#&t=";
	/**
	 * 获取讨论组成员信息
	 */
	public static String URL_GET_DISCUS_MEMBER="http://d1.web2.qq.com/channel/get_discu_info?did=#did#&vfwebqq=#vfwebqq#&clientid="+PARAM_CLIENTID+"&psessionid=#psessionid#&t=";
	
	public static void setCACHE(boolean cACHE) {
		CACHE = cACHE;
	}
	public static void setPARAM_CLIENTID(String pARAM_CLIENTID) {
		PARAM_CLIENTID = pARAM_CLIENTID;
	}
	public static void setPARAM_LOGIN2(String pARAM_LOGIN2) {
		PARAM_LOGIN2 = pARAM_LOGIN2;
	}
	public static void setPARAM_FRIENDS_LIST(String pARAM_FRIENDS_LIST) {
		PARAM_FRIENDS_LIST = pARAM_FRIENDS_LIST;
	}
	public static void setPARAM_RECENTFRIENDS_LIST(String pARAM_RECENTFRIENDS_LIST) {
		PARAM_RECENTFRIENDS_LIST = pARAM_RECENTFRIENDS_LIST;
	}
	public static void setPARAM_MESSAGE_POLL(String pARAM_MESSAGE_POLL) {
		PARAM_MESSAGE_POLL = pARAM_MESSAGE_POLL;
	}
	public static void setPARAM_MESSAGE_SEND(String pARAM_MESSAGE_SEND) {
		PARAM_MESSAGE_SEND = pARAM_MESSAGE_SEND;
	}
	public static void setFILE_PATH_QR(String fILE_PATH_QR) {
		FILE_PATH_QR = fILE_PATH_QR;
	}
	public static void setFILE_IMG_LOCAL(boolean fILE_IMG_LOCAL) {
		FILE_IMG_LOCAL = fILE_IMG_LOCAL;
	}
	public static void setFILE_PATH_LOGS(String fILE_PATH_LOGS) {
		FILE_PATH_LOGS = fILE_PATH_LOGS;
	}
	public static void setURL_GET_QR(String uRL_GET_QR) {
		URL_GET_QR = uRL_GET_QR;
	}
	public static void setURL_GET_LOGIN_POLLING(String uRL_GET_LOGIN_POLLING) {
		URL_GET_LOGIN_POLLING = uRL_GET_LOGIN_POLLING;
	}
	public static void setURL_GET_VFWEBQQ(String uRL_GET_VFWEBQQ) {
		URL_GET_VFWEBQQ = uRL_GET_VFWEBQQ;
	}
	public static void setURL_POST_LONGIN2(String uRL_POST_LONGIN2) {
		URL_POST_LONGIN2 = uRL_POST_LONGIN2;
	}
	public static void setURL_GET_SELFINFO(String uRL_GET_SELFINFO) {
		URL_GET_SELFINFO = uRL_GET_SELFINFO;
	}
	public static void setURL_GET_SELFPIC(String uRL_GET_SELFPIC) {
		URL_GET_SELFPIC = uRL_GET_SELFPIC;
	}
	public static void setURL_POST_FRIENDS(String uRL_POST_FRIENDS) {
		URL_POST_FRIENDS = uRL_POST_FRIENDS;
	}
	public static void setURL_POST_GROUP(String uRL_POST_GROUP) {
		URL_POST_GROUP = uRL_POST_GROUP;
	}
	public static void setURL_GET_DISCUS(String uRL_GET_DISCUS) {
		URL_GET_DISCUS = uRL_GET_DISCUS;
	}
	public static void setURL_GET_ONLINEFRIENDS(String uRL_GET_ONLINEFRIENDS) {
		URL_GET_ONLINEFRIENDS = uRL_GET_ONLINEFRIENDS;
	}
	public static void setURL_POST_RECENTRIENDS(String uRL_POST_RECENTRIENDS) {
		URL_POST_RECENTRIENDS = uRL_POST_RECENTRIENDS;
	}
	public static void setURL_POST_NEWMESSAGE(String uRL_POST_NEWMESSAGE) {
		URL_POST_NEWMESSAGE = uRL_POST_NEWMESSAGE;
	}
	public static void setURL_POST_SENDMESSAGE(String uRL_POST_SENDMESSAGE) {
		URL_POST_SENDMESSAGE = uRL_POST_SENDMESSAGE;
	}
	public static void setURL_POST_SENDMESSAGE_DISCUS(String uRL_POST_SENDMESSAGE_DISCUS) {
		URL_POST_SENDMESSAGE_DISCUS = uRL_POST_SENDMESSAGE_DISCUS;
	}
	public static void setURL_POST_SENDMESSAGE_GROUP(String uRL_POST_SENDMESSAGE_GROUP) {
		URL_POST_SENDMESSAGE_GROUP = uRL_POST_SENDMESSAGE_GROUP;
	}
	public static void setURL_GET_GROUP_MEMBER(String uRL_GET_GROUP_MEMBER) {
		URL_GET_GROUP_MEMBER = uRL_GET_GROUP_MEMBER;
	}
	public static void setURL_GET_DISCUS_MEMBER(String uRL_GET_DISCUS_MEMBER) {
		URL_GET_DISCUS_MEMBER = uRL_GET_DISCUS_MEMBER;
	}
	public static void setAUTO_LOGIN(boolean aUTO_LOGIN) {
		AUTO_LOGIN = aUTO_LOGIN;
	}
	
}
