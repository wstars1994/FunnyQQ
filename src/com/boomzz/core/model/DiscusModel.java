package com.boomzz.core.model;

/**
 * @author WStars
 *
 */
public class DiscusModel  extends BaseModel{

	private String name;
	
	private String did;
	
	public DiscusModel() {
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
