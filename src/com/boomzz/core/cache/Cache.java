package com.boomzz.core.cache;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.boomzz.core.Config;
import com.boomzz.core.FQQ;
import com.boomzz.core.model.BaseModel;

/**
 * @author WStars
 * 缓存
 */
public class Cache implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
	public static void persist(){
		try {
			FileOutputStream fos = new FileOutputStream("data.x");
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(cacheMap);
			byte []b = outputStream.toByteArray();
			for(int i = 0; i<b.length ; i++)
			    fos.write(b[i]);
			fos.close();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void getPersist(){
		try {
			FileInputStream file=new FileInputStream("data.x");
			InputStream inputStream=file;
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			cacheMap = (Map<String,Object>) ois.readObject();
			FQQ.cookies = (Map<String, String>) cacheMap.get("cookie");
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
	public static void main(String[] args) {
		BaseModel baseModel = new BaseModel();
		baseModel.setId("123456");
		baseModel.setNickName("忽悠局HYJ");
		baseModel.setType(1);
		baseModel.setUin("545640807");
		cacheMap.put("baseModel", baseModel);
		persist();
		getPersist();
	}
}