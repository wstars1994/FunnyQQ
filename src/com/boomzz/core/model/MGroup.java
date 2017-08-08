package com.boomzz.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WStars
 *
 */
public class MGroup extends MBase implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String gid;
	private String code;
	private String flag;
	private Map<String, Object> member = new HashMap();
	
	public Map<String, Object> getMember() {
		return member;
	}

	public void setMember(Map<String, Object> member) {
		this.member = member;
	}

	public MGroup() {
		setType(1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Override
	public String toString() {
		return "gid:"+getGid()+" name:"+getName();
	}
}
