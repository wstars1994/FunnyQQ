package com.boomzz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author WStars
 *
 */
public class DateTimeUtil {
	
	
	public DateTimeUtil(){ }
	
	public static long getTimestamp() {
		return new Date().getTime();
	}
	
	public static String getFormatDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}
