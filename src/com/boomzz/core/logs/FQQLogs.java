package com.boomzz.core.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.boomzz.util.DateTimeUtil;

/**
 * @author WStars
 *
 */
public class FQQLogs{
	
	private File logsFile=null;
	private String path="log/";
	public FQQLogs(String path) {
		this.path += path;
		logsFile=new File(this.path);
		if(!logsFile.exists()){
			try {
				logsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void writeNewLogs(String log) {
		logsFile=new File(path);
		try {
			FileWriter fileWriter = new FileWriter(logsFile,true);
			fileWriter.write(log+"\r\n");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeNewTimeLogs(String log) {
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("[");
		sBuffer.append(DateTimeUtil.getFormatDate());
		sBuffer.append("] ");
		sBuffer.append(log);
		writeNewLogs(sBuffer.toString());
	}
	public static void main(String[] args) {
		
		new FQQLogs("back.log").writeNewTimeLogs("huanhang");
	}
}
