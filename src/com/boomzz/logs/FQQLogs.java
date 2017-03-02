package com.boomzz.logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.boomzz.core.Config;
import com.boomzz.util.DateTimeUtil;

/**
 * @author WStars
 *
 */
public class FQQLogs{
	
	private File logsFile=null;
	private String path=null;
	public FQQLogs(String path) {
		this.path=path;
		logsFile=new File(path);
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
			FileWriter fileWriter = new FileWriter(logsFile);
			fileWriter.write(log, (int)logsFile.length(), log.length());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeNewTimeLogs(String log) {
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("[");
		sBuffer.append(DateTimeUtil.getFormatDate());
		sBuffer.append("]");
		sBuffer.append(log);
	}
	public void readLogs() {
		
	}
	
	public static void main(String[] args) {
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("[");
		sBuffer.append(DateTimeUtil.getFormatDate());
		sBuffer.append("]");
		sBuffer.append("TEST");
		new FQQLogs(Config.FILE_PATH_LOGS).writeNewLogs(sBuffer.toString());
	}
}
