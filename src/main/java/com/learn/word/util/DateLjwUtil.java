package com.learn.word.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateLjwUtil {

	private static final String sdf_default="yyyy.MM.dd-HH:mm:ss";
	
	/**
	 * 
	 * @param date
	 * @return "yyyy.MM.dd-HH:mm:ss"
	 */
	public static String dateToString(Date date){
		return dateToString(date, sdf_default);
		
	} 
	
	
	
	public static String dateToString(Date date, String sdf_define){
		SimpleDateFormat sdf = new SimpleDateFormat(sdf_define);
		return sdf.format(date);
		
	}
	
}