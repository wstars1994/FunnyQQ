package com.boomzz.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boomzz.core.Config;
import com.boomzz.core.cache.Cache;
import com.boomzz.core.model.CategoriesModel;
import com.boomzz.core.model.DiscusModel;
import com.boomzz.core.model.FriendsModel;
import com.boomzz.core.model.GroupModel;
import com.boomzz.core.model.InfoModel;
import com.boomzz.core.model.PtuiCBMsgModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author WStars
 *
 */
public class FQQUtil {
	
	//ptuiCB('66','0','','0','二维码未失效。(3974129836)', '');
	//ptuiCB('67','0','','0','二维码认证中。(812671429)', '');
	//ptuiCB('0','0','msg','0','tip', 'info');

	public static PtuiCBMsgModel ptuiCBMsgToModel(String msg) {
		
		int first=msg.indexOf("(");
		int last=msg.lastIndexOf(")");
		String params=msg.substring(first+1,last);
		String paramsArr[]=params.split(",");
		PtuiCBMsgModel model=null;
		if(paramsArr.length==6){
			model=new PtuiCBMsgModel();
			model.setNo(Integer.parseInt(paramsArr[0].replace("'", "")));
			model.setP1(paramsArr[1].replace("'", ""));
			model.setP2(paramsArr[2].replace("'", ""));
			model.setP3(paramsArr[3].replace("'", ""));
			model.setP4(paramsArr[4].replace("'", ""));
			model.setP5(paramsArr[5].replace("'", ""));
			return model;
		}
		return null;
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }
	
	
	/**
	 * URL 参数替换  默认格式 #name#
	 * @param url
	 * @param params
	 * @return
	 */
	public static String replace(String url,Map<String, String> params){
		
		for(String str:params.keySet()){
			url=url.replace("#"+str+"#", params.get(str));
		}
		
		return url;
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String replace(String url,String key,String value){
		
		url=url.replace("#"+key+"#",value);
		return url;
	}
	
	/**
	 * URL 参数替换 自定义格式
	 * @param url 
	 * @param params 替换参数map
	 * @param replaceStr 按自定义前后缀替换
	 * @return
	 */
	public static String replace(String url,Map<String, String> params,String replaceStr){
		
		for(String str:params.keySet()){
			url=url.replace(replaceStr+str+replaceStr, params.get(str));
		}
		
		return url;
	}
	
	/**
	 * 查找url中需要的参数
	 * @param url
	 * @param paramName 参数名称
	 * @return
	 */
	public static String findParam(String url,String paramName) {
		int index=url.indexOf(paramName+"=");
		String subs=url.substring(index,url.length());
		int spIndex=subs.indexOf("&");
		subs=spIndex==-1?subs.replace(paramName+"=", ""):subs.substring(paramName.length()+1, spIndex);
		return subs;
	}
	
	/**
	 * 查找cookies中的参数
	 * @param param
	 * @param cookies
	 * @return
	 */
	public static String findCookieParam(String param,Map<String, String> cookies) {
		for(String key : cookies.keySet()){
			if(key.equals(param))
				return cookies.get(key);
		}
		return null;
	}
	
	/**
	 * 登录json解析
	 * @param json
	 * @return
	 */
	public static Map<String, String> jsonLogin(String json){
		Map<String, String> map=new HashMap<>();
		JSONObject o=JSONObject.fromObject(json);
		if(json==null||!o.get("retcode").toString().equals("0")){
			return null;
		}
		JSONObject result=(JSONObject) o.get("result");
		map.put("psessionid", result.get("psessionid").toString());
		return map;
	}
	/**
	 * 参数Vfwebqq解析
	 * @param json
	 * @return
	 */
	public static String jsonVfwebqq(String json) {
		JSONObject o=JSONObject.fromObject(json);
		if(json==null||!o.get("retcode").toString().equals("0")){
			return null;
		}
		JSONObject result=(JSONObject) o.get("result");
		return result.getString("vfwebqq");
	}
	
	/**
	 * 用户信息解析
	 * @param json
	 * @return
	 */
	public static InfoModel jsonInfo(String json){
		InfoModel info=new InfoModel();
		JSONObject o=JSONObject.fromObject(json);
		if(json==null||!o.get("retcode").toString().equals("0")){
			return null;
		}
		JSONObject result=(JSONObject) o.get("result");
		JSONObject birthday=(JSONObject) result.get("birthday");
		info.setBirthday(birthday.getString("year")+"-"+birthday.getString("month")+"-"+birthday.getString("day"));
		info.setAllow(result.get("allow").toString());
		info.setBlood(result.get("blood").toString());
		info.setCity(result.get("city").toString());
		info.setCollege(result.get("college").toString());
		info.setConstel(result.get("constel").toString());
		info.setCountry(result.get("country").toString());
		info.setEmail(result.get("email").toString());
		info.setFace(result.get("face").toString());
		info.setGender(result.get("gender").toString());
		info.setMobile(result.get("mobile").toString());
		info.setOccupation(result.get("occupation").toString());
		info.setPhone(result.get("phone").toString());
		info.setProvince(result.get("province").toString());
		info.setShengxiao(result.get("shengxiao").toString());
		info.setType(0);
		return info;
	}

	/**
	 * 获取好友列表
	 * @param json
	 * @return
	 */
	public static List<FriendsModel> jsonFriendsList(String json) {
		Map<String, FriendsModel> mapping = new HashMap<>();
		List<FriendsModel> friendsList = new ArrayList<>();
		if(checkRetcode(json)){
			JSONObject o=JSONObject.fromObject(json);
			if(isNotNull(o.get("result"))){
				JSONObject result=(JSONObject) o.get("result");
				//好友列表
				if(isNotNull(result.get("friends"))){
					JSONArray friends = (JSONArray) result.get("friends");
					for(Object f:friends){
						JSONObject object = (JSONObject) f;
						FriendsModel fModel=new FriendsModel();
						fModel.setUin(object.getString("uin"));
						fModel.setCategories(object.getString("categories"));
						fModel.setFlag(object.getString("flag"));
						mapping.put(object.getString("uin"), fModel);
					}
				}
				//备注
				if(isNotNull(result.get("marknames"))){
					JSONArray marknames = (JSONArray) result.get("marknames");
					for(Object m:marknames){
						JSONObject object = (JSONObject) m;
						if(mapping.containsKey(object.getString("uin"))){
							mapping.get(object.getString("uin")).setMarkName(object.getString("markname"));
						}
					}
				}
				//信息
				if(isNotNull(result.get("info"))){
					JSONArray info = (JSONArray) result.get("info");
					for(Object m:info){
						JSONObject object = (JSONObject) m;
						if(mapping.containsKey(object.getString("uin"))){
							mapping.get(object.getString("uin")).setNickName(object.getString("nick"));
						}
					}
				}
				//VIP信息
				if(isNotNull(result.get("vipinfo"))){
					JSONArray vipinfo = (JSONArray) result.get("vipinfo");
					for(Object m:vipinfo){
						JSONObject object = (JSONObject) m;
						if(mapping.containsKey(object.getString("u"))){
							FriendsModel friendsModel = mapping.get(object.getString("u"));
							friendsModel.setVip_level(object.getInt("vip_level"));
							friendsModel.setVip(object.getInt("vip_level")==1?true:false);
							mapping.put(object.getString("u"), friendsModel);
						}
					}
				}
				//分组列表
				if(isNotNull(result.get("categories"))){
					JSONArray categories = (JSONArray) result.get("categories");
					List<CategoriesModel> categoriesList = new ArrayList<>();
					for(Object m:categories){
						JSONObject object = (JSONObject) m;
						CategoriesModel categoriesModel=new CategoriesModel();
						categoriesModel.setIndex(object.getInt("index"));
						categoriesModel.setSort(object.getInt("sort"));
						categoriesModel.setName(object.getString("name"));
						categoriesList.add(categoriesModel);
					}
					Cache.putCache(Config.CACHE_KEY_CATEGORIES, categoriesList);
				}
			}
		}
		for(String uin:mapping.keySet())
			friendsList.add(mapping.get(uin));
		return friendsList;
	}
	/**
	 * 获取在线好友
	 * @param json
	 * @return
	 */
	public static List<String> jsonOnlineFriendsList(String json) {
		List<String> onlinUin = new ArrayList<>();
		if(checkRetcode(json)){
			JSONObject o=JSONObject.fromObject(json);
			JSONArray result=(JSONArray) o.get("result");
			for(Object m:result){
				JSONObject object = (JSONObject) m;
				onlinUin.add(object.getString("uin"));
			}
		}
		return onlinUin;
	}
	public static List<DiscusModel> jsonDiscusList(String json){
		List<DiscusModel> discusList = new ArrayList<>();
		if(checkRetcode(json)){
			JSONObject o=JSONObject.fromObject(json);
			JSONObject result=(JSONObject) o.get("result");
			JSONArray dnamelist=(JSONArray)result.get("dnamelist");
			for(Object m:dnamelist){
				JSONObject object = (JSONObject) m;
				DiscusModel model = new DiscusModel();
				model.setDid(object.getString("did"));
				model.setName(object.getString("name"));
				discusList.add(model);
			}
		}
		return discusList;
	}
	public static List<GroupModel> jsonGroupList(String json){
		List<GroupModel> discusList = new ArrayList<>();
		if(checkRetcode(json)){
			JSONObject o=JSONObject.fromObject(json);
			JSONObject result=(JSONObject) o.get("result");
			JSONArray dnamelist=(JSONArray)result.get("gnamelist");
			for(Object m:dnamelist){
				JSONObject object = (JSONObject) m;
				GroupModel model = new GroupModel();
				model.setName(object.getString("name"));
				model.setCode(object.getString("code"));
				model.setFlag(object.getString("flag"));
				model.setGid(object.getString("gid"));
				discusList.add(model);
			}
		}
		return discusList;
	}
	
	private static boolean checkRetcode(String json){
		if(json==null) return false;
		try {
			JSONObject o=JSONObject.fromObject(json);
			if(o.get("retcode")!=null&&o.get("retcode").toString().equals("0")){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private static boolean isNotNull(Object object){
		if(object==null){
			return false;
		}
		return true;
	}
}
