package com.boomzz.core.model;

import java.io.Serializable;

/**
 * 讨论组
 * @author WStars
 */
public class MDiscus  extends MBase implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String did;
	
	public MDiscus() {
		setType(2);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
	
	@Override
	public String toString() {
		return "名称:"+getName()+" did:"+getDid();
	}
}
