package com.boomzz.core.model;

import java.io.Serializable;

/**
 * @author WStars
 *
 */
public class GroupModel extends BaseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String gid;
	private String code;
	private String flag;
	
	public GroupModel() {
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
