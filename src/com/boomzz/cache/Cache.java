package com.boomzz.cache;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.config.Config;

/**
 * @author WStars
 * 缓存
 */
public class Cache{
	
	private static Map<String,Object> cacheMap=new HashMap<>();
	
	public static void putCache(String uniqueKey,Object o){
		if(Config.CACHE)
			cacheMap.put(uniqueKey, o);
	}
	
	public static Object getCache(String uniqueKey){
		return	Config.CACHE?cacheMap.get(uniqueKey):null;
	}
	
	public void removeCache(String uniqueKey){
		if(Config.CACHE)
			cacheMap.remove(uniqueKey);
	}
	
	public void clear(){
		if(Config.CACHE)
			cacheMap.clear();
	}
}