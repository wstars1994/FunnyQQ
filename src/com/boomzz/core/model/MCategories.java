package com.boomzz.core.model;

import java.io.Serializable;

/**
 *分组
 */
public class MCategories implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int index ; 
	private int sort ; 
	private String name;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
