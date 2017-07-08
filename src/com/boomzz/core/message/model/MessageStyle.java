package com.boomzz.core.message.model;

public class MessageStyle {

	private String color = "000000";
	private String name = "微软雅黑";
	private String size = "10";
	private int []style = {0,0,0};
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int[] getStyle() {
		return style;
	}
	public void setStyle(int[] style) {
		this.style = style;
	}
	
	
	
}
