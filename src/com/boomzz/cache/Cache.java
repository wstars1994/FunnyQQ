package com.boomzz.cache;

import java.util.HashMap;
import java.util.Map;

import com.boomzz.config.Config;

public class Cache{
	
	private static Map<String,Object> cacheMap=new HashMap<>();
	
	public static void putCache(String uniqueKey,Object o){
		if(Config.cache)
			cacheMap.put(uniqueKey, o);
	}
	
	public static Object getCache(String uniqueKey){
		return	Config.cache?cacheMap.get(uniqueKey):null;
	}
	
	public void removeCache(String uniqueKey){
		if(Config.cache)
			cacheMap.remove(uniqueKey);
	}
	
	public void clear(){
		if(Config.cache)
			cacheMap.clear();
	}
}