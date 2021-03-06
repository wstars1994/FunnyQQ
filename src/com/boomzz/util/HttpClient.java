package com.boomzz.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpClient {
	
	private final static Logger logger = LogManager.getLogger(HttpClient.class);
	
	public static String get(String url,Map<String, String> cookies){
		//创建连接
		try {
			HttpURLConnection connection = getConnection(url,"GET","application/json;charset=UTF-8",false,cookies);
			connection.connect();
			//读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            
            logger.info(url+" \n"+sb.toString());
            
            Map<String, List<String>> headerFields = connection.getHeaderFields();
//            if(headerFields.get("Location")!=null&&headerFields.get("Location").size()>0){
//            	return get(headerFields.get("Location").get(0), cookies);
//            }
            for(String h: headerFields.keySet()){
            	if("Set-Cookie".equals(h)){
            		List<String> list = headerFields.get(h);
            		for(String s:list){
            			String cookiesArry[]=s.split(";");
                		for(String strCookie:cookiesArry){
                			String c[]=strCookie.split("=");
                			if(c.length==2){
                				//除去不需要的
                				if(!c[0].contains("EXPIRES")&&!c[0].contains("PATH")&&!c[0].contains("DOMAIN"))
                    				cookies.put(c[0], c[1]);
                			}
                		}
            		}
            	}
            }
            reader.close();
            
        	return sb.toString();
            // 断开连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String post(String url,Map<String, String> params,Map<String, String> cookies){
		//创建连接
		try {
			HttpURLConnection connection = getConnection(url,"POST","application/json;charset=UTF-8",false,cookies);
			connection.connect();
			PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			printWriter.write(urlParamsStr(params));//post的参数 xx=xx&yy=yy
			// flush输出流的缓冲
			printWriter.flush();
			//读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			
			logger.info(url+" \n"+sb.toString());
			
			Map<String, List<String>> headerFields = connection.getHeaderFields();
			for(String h: headerFields.keySet()){
				if("Set-Cookie".equals(h)){
					List<String> list = headerFields.get(h);
					for(String s:list){
						String cookiesArry[]=s.split(";");
						for(String strCookie:cookiesArry){
							String c[]=strCookie.split("=");
							if(c.length==2){
								//除去不需要的
								if(cookies!=null&&!c[0].contains("EXPIRES")&&!c[0].contains("PATH")&&!c[0].contains("DOMAIN"))
									cookies.put(c[0], c[1]);
							}
						}
					}
				}
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求失败");
		}
		return null;
	}
	
	public static String postHttps(String urlStr,Map<String, String> params,Map<String, String> cookies){
		//创建连接
		try {
			URL url = new URL(urlStr);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Referer","https://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1");
			if(cookies!=null){
				String cooStr="";
				for(String c:cookies.keySet()){
					cooStr+=c+"="+cookies.get(c)+";";
				}
				connection.addRequestProperty("Cookie",cooStr);
			}
			connection.connect();
			
			PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			printWriter.write(urlParamsStr(params));//post的参数 xx=xx&yy=yy
			// flush输出流的缓冲
			printWriter.flush();
			//读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			
			logger.info(url+" \n"+sb.toString());
			
			Map<String, List<String>> headerFields = connection.getHeaderFields();
			for(String h: headerFields.keySet()){
				if("Set-Cookie".equals(h)){
					List<String> list = headerFields.get(h);
					for(String s:list){
						String cookiesArry[]=s.split(";");
						for(String strCookie:cookiesArry){
							String c[]=strCookie.split("=");
							if(c.length==2){
								//除去不需要的
								if(!c[0].contains("EXPIRES")&&!c[0].contains("PATH")&&!c[0].contains("DOMAIN"))
									cookies.put(c[0], c[1]);
							}
						}
					}
				}
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream getBackAndCookieForQR(String url, String path,Map<String, String> cookies,boolean local) throws Exception{
		HttpURLConnection connection = getConnection(url,"GET","application/json;charset=UTF-8",false,cookies);
		connection.connect();
		InputStream inputStream=connection.getInputStream();
		Map<String, List<String>> headerFields = connection.getHeaderFields();
        for(String h: headerFields.keySet()){
        	if("Set-Cookie".equals(h)){
        		List<String> list = headerFields.get(h);
        		for(String s:list){
        			if(s.matches("^.*qrsig=.*$")){
        				Pattern p = Pattern.compile("qrsig=[^;]+");
        				Matcher m = p.matcher(s);
        				if(m.find())
        					cookies.put("qrsig", m.group().split("=")[1]);
        			}
        		}
        	}
        }
        if(local){
    		try {
    			byte[] data =  FQQUtil.readInputStream(inputStream);
    			File imageFile = new File(path);
    			if(imageFile.exists()){
    				imageFile.delete();
    			}
    			//创建输出流  
    			FileOutputStream outStream = new FileOutputStream(imageFile);  
    			//写入数据  
    			outStream.write(data);
    			//关闭输出流  
    			outStream.close(); 
    		}catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
		return inputStream;
	}
	private static HttpURLConnection getConnection(String urlStr,String method,String ContentType,boolean rediect,Map<String, String> cookies) throws Exception{
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
//		connection.setRequestProperty("Content-Type", ContentType);
		connection.setRequestMethod(method);
		connection.setInstanceFollowRedirects(rediect);
		connection.addRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");
		if(cookies!=null){
			String cooStr="";
			for(String c:cookies.keySet()){
				cooStr+=c+"="+cookies.get(c)+";";
			}
			connection.addRequestProperty("Cookie",cooStr);
		}
		if(urlStr.contains("online_buddies2")||urlStr.contains("recent_list2"))
			connection.addRequestProperty("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2"); 
		else 
			connection.addRequestProperty("Referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
//		if(urlStr.contains("https://user.qzone.qq.com/")){
//			connection.addRequestProperty("Referer","https://qzs.qq.com/qzone/v5/loginsucc.html?para=izone");
//		}
		return connection;
	}
	private static String urlParamsStr(Map<String, String> params) throws UnsupportedEncodingException{
		String url = "";
		for(String key:params.keySet()){
			url+=key+"="+URLEncoder.encode(params.get(key), "utf-8")+"&";
		}
		if(!url.equals(""))
			url = url.substring(0,url.length()-1);
		return url;
	}
	
	public static void main(String[] args) {
		String url="https://user.qzone.qq.com/545640807";
		Map<String, String> cookies = new HashMap<>();
		String string = get(url, cookies);
		System.out.println(string);
	}
}