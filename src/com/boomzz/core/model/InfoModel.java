package com.boomzz.core.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WStars
 * 用户附加信息
 */
public class InfoModel extends BaseModel{
	
	private String birthday;
	private String face;
	private String phone;
	/** 学历 */
	private String occupation;
	private String allow;
	private String college;
	private String blood;
	/** 星座 */
	private String constel;
	private String country;
	private String city;
	private String province;
	private String shengxiao;
	private String email;
	private String gender;
	private String mobile;
	
	private Map<String,String> genderMap,bloodMap,shengxiaoMap,constelMap;
	public InfoModel() {
		genderMap=new HashMap<>();
		bloodMap=new HashMap<>();
		shengxiaoMap=new HashMap<>();
		constelMap=new HashMap<>();
		
		genderMap.put("male", "男");
		genderMap.put("female", "女");

		bloodMap.put("1", "A");
		bloodMap.put("2", "B");
		bloodMap.put("3", "O");
		
		shengxiaoMap.put("1","鼠");
		shengxiaoMap.put("2","牛");
		shengxiaoMap.put("3","虎");
		shengxiaoMap.put("4","兔");
		shengxiaoMap.put("5","龙");
		shengxiaoMap.put("6","蛇");
		shengxiaoMap.put("7","马");
		shengxiaoMap.put("8","羊");
		shengxiaoMap.put("9","猴");
		shengxiaoMap.put("10","鸡");
		shengxiaoMap.put("11","狗");
		shengxiaoMap.put("12","猪");
		
		constelMap.put("1","水瓶座");
		constelMap.put("2","双鱼座");
		constelMap.put("3","白羊座");
		constelMap.put("4","金牛座");
		constelMap.put("5","双子座");
		constelMap.put("6","巨蟹座");
		constelMap.put("7","狮子座");
		constelMap.put("8","处女座");
		constelMap.put("9","天秤座");
		constelMap.put("10","天蝎座");
		constelMap.put("11","射手座");
		constelMap.put("12","摩羯座");
	}
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getAllow() {
		return allow;
	}
	public void setAllow(String allow) {
		this.allow = allow;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getBlood() {
		return bloodMap.get(blood);
	}
	public void setBlood(String blood) {
		this.blood = blood;
	}
	public String getConstel() {
		return constelMap.get(constel);
	}
	public void setConstel(String constel) {
		this.constel = constel;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getShengxiao() {
		return shengxiaoMap.get(shengxiao);
	}
	public void setShengxiao(String shengxiao) {
		this.shengxiao = shengxiao;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return genderMap.get(gender);
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return super.toString();
	}
}
