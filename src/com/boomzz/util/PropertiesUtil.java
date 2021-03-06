package com.boomzz.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.boomzz.core.Config;

public class PropertiesUtil {
	
	private static String filePath="login.properties";
	
	public static String GetValueByKey(String key) {
		Properties pps = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
			pps.load(in);
			String value = pps.getProperty(key);
			in.close();
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	//写入Properties信息
	public static void WriteProperties(String pKey, String pValue) throws IOException {
		if(Config.AUTO_LOGIN){
			Properties pps = new Properties();

			String url = PropertiesUtil.class.getClassLoader().getResource(filePath).toString();
			url = url.substring(6,url.length());
			
			// 从输入流中读取属性列表（键和元素对）
			pps.load(new FileInputStream(url));
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream out = new FileOutputStream(url);
			pps.setProperty(pKey, pValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			pps.store(out, "Update " + pKey + " name");
			out.close();
		}
		
	}
}
