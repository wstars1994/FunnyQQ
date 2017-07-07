package com.boomzz.core.cache.thread;

import com.boomzz.core.message.Message;

public class TFriendsOnline extends Thread{
	
	private Message msg ;
	private long lastRun = 0;
	private int runTime = 10000;
	public TFriendsOnline(Message msg) {
		this.msg  = msg;
	}
	
	@Override
	public void run() {

		while(true){
			if(lastRun==0){
				msg.getFrientList();
			}
			if(lastRun+runTime>=System.currentTimeMillis()){
				msg.getFrientList();
			}
			lastRun = System.currentTimeMillis();
		}
	}
}
