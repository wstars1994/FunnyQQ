/**
 * 
 * 项目名称:[FunnyQQ]
 * 包:	 [com.boomzz.util]
 * 类名称: [TencentBackMsgUtil]
 * 类描述: [一句话描述该类的功能]
 * 创建人: [王新晨]
 * 创建时间:[2017年2月28日 下午2:15:28]
 * 修改人: [王新晨]
 * 修改时间:[2017年2月28日 下午2:15:28]
 * 修改备注:[说明本次修改内容]  
 * 版本:	 [v1.0]   
 * 
 */
package com.boomzz.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import com.boomzz.model.PtuiCBMsgModel;

import net.sf.json.JSONObject;

/**
 * @author WStars
 *
 */
public class FunnyQQUtil {
	
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
	
	public static String findCookieParam(String param,Map<String, String> cookies) {
		for(String key : cookies.keySet()){
			if(key.equals(param))
				return cookies.get(key);
		}
		return null;
	}
	
	public static String findParamVfwebqq(String json){
		json="{\"retcode\":0,\"result\":{\"vfwebqq\":\"aacc2536671431516f9cd91507094419589576e59e852ba41ef74b17c3cfdedc60bd2521575fa9d3\"}}";
		JSONObject o=JSONObject.fromObject(json);
		System.out.println(o.get("retcode"));
		return "";
	}
	public static void main(String[] args) {
		FunnyQQUtil.findParamVfwebqq("");
	}
}
