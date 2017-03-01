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

import com.boomzz.model.PtuiCBMsgModel;

/**
 * @author WStars
 *
 */
public class TencentBackMsgUtil {
	
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
	
//	
//	public static void main(String[] args) {
//		String string="ptuiCB('66','0','','0','二维码未失效。(3974129836)', '');";
//		TencentBackMsgUtil.ptuiCBMsgToModel(string);
//	}
}
