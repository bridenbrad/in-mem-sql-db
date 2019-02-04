/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.date;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class DateUtil {
	
	public static final String DEFAULT_ISO_TIME_PATTERN 	 = "HH:mm:ss.SSS";
	public static final String DEFAULT_ISO_DATE_PATTERN 	 = "yyyy-MM-dd";
	public static final String DEFAULT_ISO_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static final SimpleDateFormat ISO_TIME_FORMATTER       = new SimpleDateFormat(DEFAULT_ISO_TIME_PATTERN);
	private static final SimpleDateFormat ISO_DATE_FORMATTER      = new SimpleDateFormat(DEFAULT_ISO_DATE_PATTERN);
	private static final SimpleDateFormat ISO_TIMESTAMP_FORMATTER = new SimpleDateFormat(DEFAULT_ISO_TIMESTAMP_PATTERN);

	public static java.sql.Date getSqlDateFromDateNumber(short dateNumber) {
		return new java.sql.Date(dateNumber * (1000L * 60 * 60 * 24));
	}
	
	public static Time getSqlTimeFromTimeNumber(int time) {
		int leftover = time;
		
		final int hour   = leftover / (60 * 60 * 1000);
		leftover -= hour * 60 * 60 * 1000;
		
		final int minute = leftover / (60 * 1000);
		leftover -= minute * 60 * 1000;
		
		final int second = leftover / 1000;
		leftover -= second * 1000;
		
		final int milli  = leftover;
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, milli);
		
		return new Time(calendar.getTimeInMillis());
	}
	
	public static int getTimeNumberFromSqlTime(Time time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		final int hour   = calendar.get(Calendar.HOUR);
		final int minute = calendar.get(Calendar.MINUTE);
		final int second = calendar.get(Calendar.SECOND);
		final int milli  = calendar.get(Calendar.MILLISECOND);
		return hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + milli;
	}
	
	public static synchronized int parseTime(String time) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ISO_TIME_FORMATTER.parse(time));
			int hour   = calendar.get(Calendar.HOUR);
			int minute = calendar.get(Calendar.MINUTE);
			int second = calendar.get(Calendar.SECOND);
			int milli  = calendar.get(Calendar.MILLISECOND);
			return hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000 + milli;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static synchronized String timestampToString(long timestamp) {
		synchronized (ISO_TIMESTAMP_FORMATTER) {			
			return ISO_TIMESTAMP_FORMATTER.format(new Date(timestamp));
		}
	}

	public static long parseTimestamp(String timestampString) {
		try {
			synchronized (ISO_TIMESTAMP_FORMATTER) {				
				return ISO_TIMESTAMP_FORMATTER.parse(timestampString).getTime();
			}
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse timestamp from '" + timestampString + "'");
		}
	}
	
	/**
	 * Assign short number to given date.
	 */
	public static short parseDateNumber(String dateString) {
		try {
			synchronized (ISO_DATE_FORMATTER) {				
				return timestampToDayNumber(ISO_DATE_FORMATTER.parse(dateString).getTime() + (1000 * 60 * 60 * 24));
			}
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse '" + dateString + "'");
		}
	}
	
	public static synchronized String dateNumberToString(short dateNumber) {
		synchronized (ISO_DATE_FORMATTER) {			
			return ISO_DATE_FORMATTER.format(getSqlDateFromDateNumber(dateNumber));
		}
	}
	
	public static long dayNumberToTimestamp(short dateNumber) {
		return dateNumber * (1000L * 60 * 60 * 24);
	}
	
	public static short timestampToDayNumber(long timestamp) {
		long result = timestamp / (1000 * 60 * 60 * 24);
		if(result < 0 || result > Short.MAX_VALUE) {
			throw new IllegalArgumentException("Wrong date input '" + result + "'");
		}
		return (short)result;
	}
	
	private DateUtil() {
	}
}
