package com.boomzz.core.message.model;

public class MMsgAccept{

	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	private String fromUin;
	
	private String sendUin;
	
	private String toUin;
	
	private long time;

	private MessageStyle style;
	
	private int msgType;

	private String msgId;
	
	private String pollType;
	
	
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
	
	
}
