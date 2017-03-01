package com.boomzz.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.boomzz.core.QRCodeLogin;

public class HttpClientUtil {

	/**
     * 处理get请求.
     * @param url  请求路径
     */
    public static String get(String url){
    	//实例化httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化get方法
        HttpGet httpget = new HttpGet(url);
        if(QRCodeLogin.cookies!=null){
        	String cooStr="";
        	for(String c:QRCodeLogin.cookies.keySet()){
        		cooStr+=c+"="+QRCodeLogin.cookies.get(c)+";";
        	}
        	httpget.addHeader("Cookie",cooStr);
        }
        //请求结果
        CloseableHttpResponse response = null;
        String content ="";
        try {
            //执行get方法
            response = httpclient.execute(httpget);
            for(Header h: response.getAllHeaders()){
            	if("Set-Cookie".equals(h.getName())){
            		String cookies[]=h.getValue().split(";");
            		for(String s:cookies){
            			String c[]=s.split("=");
            			if(c.length==2)
            				QRCodeLogin.cookies.put(c[0], c[1]);
            		}
            	}
            }
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(),"utf-8");
            }
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
	/**
     * 处理get请求 获取图片.
     * @param url  请求路径
     */
    public static String getBackAndCookieForQR(String url){
    	//实例化httpclient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化get方法
        HttpGet httpget = new HttpGet(url);
        //请求结果
        CloseableHttpResponse response = null;
        String content ="";
        try {
            //执行get方法
            response = httpclient.execute(httpget);
            for(Header h: response.getAllHeaders()){
            	if("Set-Cookie".equals(h.getName())){
            		String cookies[]=h.getValue().split(";");
            		for(String s:cookies){
            			String c[]=s.split("=");
            			QRCodeLogin.cookies.put(c[0], c[1]);
            		}
            	}
            }
            if(response.getStatusLine().getStatusCode()==200){
            	InputStream inputStream=response.getEntity().getContent();
				try {
					byte[] data =   QRImageUtil.readInputStream(inputStream);
					File imageFile = new File("qr.jpg");
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
            
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    /**
     * 处理post请求.
     * @param url  请求路径
     * @param params  参数
     * @return  json
     */
    public static String post(String url,Map<String, String> params) {
        //实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //实例化post方法
        HttpPost httpPost = new HttpPost(url);
        if(QRCodeLogin.cookies!=null){
        	String cooStr="";
        	for(String c:QRCodeLogin.cookies.keySet()){
        		cooStr+=c+"="+QRCodeLogin.cookies.get(c)+";";
        	}
        	httpPost.addHeader("Cookie",cooStr);
        }
        //处理参数
        List<NameValuePair> nvps = new ArrayList <NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }
        //结果
        CloseableHttpResponse response = null;
        String content="";
        try {
            //提交的参数
            UrlEncodedFormEntity uefEntity  = new UrlEncodedFormEntity(nvps, "UTF-8");
            //将参数给post方法
            httpPost.setEntity(uefEntity);
            //执行post方法
            response = httpclient.execute(httpPost);
            for(Header h: response.getAllHeaders()){
            	if("Set-Cookie".equals(h.getName())){
            		String cookies[]=h.getValue().split(";");
            		for(String s:cookies){
            			String c[]=s.split("=");
            			if(c.length==2)
            				QRCodeLogin.cookies.put(c[0], c[1]);
            		}
            	}
            }
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(),"utf-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
