package com.boomzz.core;

import java.io.InputStream;

import com.boomzz.core.message.Message;
import com.boomzz.core.message.model.MMsgAccept;

public interface IQQListener {
	
	/**
	 * 二维码流
	 * @param inputStream
	 */
	public void imageStream(InputStream inputStream);
	
	/**
	 * 新消息
	 * @param msg
	 * @param message
	 */
	public void acceptMessage(MMsgAccept msg, Message message);
	
}
