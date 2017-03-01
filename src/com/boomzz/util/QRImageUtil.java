/**
 * 
 * 项目名称:[FunnyQQ]
 * 包:	 [com.boomzz.util]
 * 类名称: [QRImageUtil]
 * 类描述: [一句话描述该类的功能]
 * 创建人: [王新晨]
 * 创建时间:[2017年2月28日 上午11:38:37]
 * 修改人: [王新晨]
 * 修改时间:[2017年2月28日 上午11:38:37]
 * 修改备注:[说明本次修改内容]  
 * 版本:	 [v1.0]   
 * 
 */
package com.boomzz.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author WStars
 *
 */
public class QRImageUtil {
	
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
}
