package com.boomzz.core.model;

/**
 * @author WStars
 *
 */
public class DiscusModel  extends BaseModel{

	private int count;
	
	public DiscusModel() {
		setType(2);
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
