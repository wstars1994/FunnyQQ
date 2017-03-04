package com.boomzz.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache{
	
	private static Map<String,Object> cacheMap=new HashMap<>();
	
	public static void putCache(String c,Object o){
		cacheMap.put(c, o);
	}
	
	public static Object getCache(String c){
		return cacheMap.get(c);
	}
	
	public void removeCache(String c){
		cacheMap.remove(c);
	}
	
	public void clear(){
		cacheMap.clear();
	}
}