package com.boomzz.core.model;
/**
 * @author WStars
 * 登录前返回消息
 */
public class PtuiCBMsgModel {
	
	//参数注释均为猜测
	
	/** 消息类型代码*/
	private int no;
	
	private String p1;
	
	private String p2;
	
	private String p3;

	private String p4;

	private String p5;
	/** 提示信息*/
	private String tip;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getP1() {
		return p1;
	}
	public void setP1(String p1) {
		this.p1 = p1;
	}
	public String getP2() {
		return p2;
	}
	public void setP2(String p2) {
		this.p2 = p2;
	}
	public String getP3() {
		return p3;
	}
	public void setP3(String p3) {
		this.p3 = p3;
	}
	public String getP4() {
		return p4;
	}
	public void setP4(String p4) {
		this.p4 = p4;
	}
	public String getP5() {
		return p5;
	}
	public void setP5(String p5) {
		this.p5 = p5;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	
}
