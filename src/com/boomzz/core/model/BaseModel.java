package com.boomzz.core.model;

import java.io.Serializable;

/**
 * @author WStars
 *
 */
public class BaseModel implements Serializable{
	/**
	 * 真实号
	 */
	private String id;
	/**
	 * 标识 非真实QQ号,群号,讨论组号  登录账号的ID和uin相同
	 */
	private String uin;
	/**
	 * 0:用户 1:群 2:讨论组
	 */
	private int type;
	
	private String nickName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
