package com.boomzz.core;

import java.io.InputStream;

import com.boomzz.core.message.Message;
import com.boomzz.core.message.model.MMsgAccept;

public interface IQQListener {
	
	public void imageStream(InputStream inputStream);
	
	public void acceptMessage(MMsgAccept msg, Message message);
	
}
