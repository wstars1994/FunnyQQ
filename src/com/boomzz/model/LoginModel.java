package com.boomzz.model;
/**
 * @author WStars
 * 登录的一些必要信息 单例
 */
public class LoginModel {
	
	
	/**
	 * 身份id 一般为qq号
	 */
	private String uid;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 扫码登陆签名
	 */
	private String qrSig;
	
	private String ptwebqq;
	
	private String vfwebqq;
	
	private String clientId;

	private String psessionid;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getQrSig() {
		return qrSig;
	}

	public void setQrSig(String qrSig) {
		this.qrSig = qrSig;
	}

	public String getPtwebqq() {
		return ptwebqq;
	}

	public void setPtwebqq(String ptwebqq) {
		this.ptwebqq = ptwebqq;
	}

	public String getVfwebqq() {
		return vfwebqq;
	}

	public void setVfwebqq(String vfwebqq) {
		this.vfwebqq = vfwebqq;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPsessionid() {
		return psessionid;
	}

	public void setPsessionid(String psessionid) {
		this.psessionid = psessionid;
	}

}
