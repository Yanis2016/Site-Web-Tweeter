package com.twister.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTools {
	
	
	private static final GregorianCalendar gc = new java.util.GregorianCalendar(TimeZone.getTimeZone("UCT+5 "));
	
	
	public static String getFormatedDateAfterNHour(int n) {
		gc.add(Calendar.HOUR, +n);
		String DateFin = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format( gc.getTime());
		return DateFin;
	}
	
	
	/**
	 * @param n
	 * @return Date after n hour
	 */
	public static Date getDateAfterNHour(int n) {
		gc.add(Calendar.HOUR, +n);
		return gc.getTime();
	}
}
