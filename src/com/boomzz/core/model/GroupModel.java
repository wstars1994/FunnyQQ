package com.boomzz.core.model;

/**
 * @author WStars
 *
 */
public class GroupModel extends BaseModel{

	private int count;
	
	public GroupModel() {
		setType(1);
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
