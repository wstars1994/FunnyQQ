package com.boomzz.core.message.model;

public class MMsgAccept{

	private static final long serialVersionUID = 1L;
	//消息文本
	private String msg;
	//来源
	private String fromUin;
	//发送者的Uin
	private String sendUin;
	//接收者的QQ
	private String toUin;
	//时间
	private long time;
	//消息风格
	private MessageStyle style;
	//消息类型 1:个人消息 4:群消息 5:讨论组消息
	private int msgType;
	//消息ID
	private String msgId;
	
	private String pollType;
	//是否为@消息
	private boolean isAt = false;
	
	public String getPollType() {
		return pollType;
	}

	public void setPollType(String pollType) {
		this.pollType = pollType;
	}

	public MessageStyle getStyle() {
		return style;
	}

	public void setStyle(MessageStyle style) {
		this.style = style;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public String getFromUin() {
		return fromUin;
	}

	public void setFromUin(String fromUin) {
		this.fromUin = fromUin;
	}

	public String getSendUin() {
		return sendUin;
	}

	public void setSendUin(String sendUin) {
		this.sendUin = sendUin;
	}

	public String getToUin() {
		return toUin;
	}

	public void setToUin(String toUin) {
		this.toUin = toUin;
	}

	public boolean isAt() {
		return isAt;
	}

	public void setAt(boolean isAt) {
		this.isAt = isAt;
	}
	
	
}
