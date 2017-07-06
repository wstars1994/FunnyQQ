package com.boomzz.core.model;

import com.boomzz.core.message.MessageStyle;

public class MessageModel extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	private BaseModel fromFriends;
	
	private BaseModel toFriends;
	
	private long time;

	private MessageStyle style;
	
	private String msgType;

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

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
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

	public BaseModel getFromFriends() {
		return fromFriends;
	}

	public void setFromFriends(BaseModel fromFriends) {
		this.fromFriends = fromFriends;
	}

	public BaseModel getToFriends() {
		return toFriends;
	}

	public void setToFriends(BaseModel toFriends) {
		this.toFriends = toFriends;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
