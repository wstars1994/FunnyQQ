package com.boomzz.core.model;

import java.io.Serializable;

/**
 * @author WStars
 * 好友列表
 */
public class MFriends extends MBase implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 备注
	 */
	private String markName;
	
	private String flag;
	
	private String categories;
	
	/**
	 * 1:在线 0:不在线
	 */
	private boolean online;
	
	/**
	 * 1:是 0:不是
	 */
	private boolean vip;
	
	private int vip_level;
	
	public int getVip_level() {
		return vip_level;
	}

	public void setVip_level(int vip_level) {
		this.vip_level = vip_level;
	}

	public MFriends() {
		setType(0);
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public boolean getOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public String getMarkName() {
		return markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}
	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}
	@Override
	public String toString() {
		return "uin:"+getUin()+"昵称:"+getNickName()+",备注:"+getMarkName();
	}
}
