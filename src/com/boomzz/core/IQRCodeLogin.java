package com.boomzz.core;

/**
 * @author WStars
 * 手机扫描二维码登录
 */
public interface IQRCodeLogin extends IFunnyQQBase{
	/**
	 * 获取二维码图片 保存在项目主目录
	 * @return
	 */
	public boolean getQRCodeForMobile();
	
	/**
	 * 获取qrsig加密参数
	 * @return
	 */
	public String getPtqrToken();
	
	/**
	 * 开始登录 轮询扫码状态
	 * @param url
	 * @return
	 */
	public boolean loginPolling(String url);
}
