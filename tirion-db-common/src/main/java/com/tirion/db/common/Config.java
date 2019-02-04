/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.common;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Patterns for date, time & timestamp.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Config implements Serializable {
	
	public static final Config DEFAULT = new Config();
	
	private static final long serialVersionUID = 6303712532892451765L;

	public static enum Key {
		/**
		 * Pattern of date format in text files.
		 */
		DATE_PATTERN("date.pattern"),
		
		/**
		 * Pattern of time format in text files.
		 */
		TIME_PATTERN("time.pattern"),
		
		/**
		 * Pattern of timestamp format in text files.
		 */
		TIMESTAMP_PATTERN("timestamp.pattern"),
		
		/**
		 * Separator of values in single line of text file.
		 */
		SEPARATOR("separator"),

		/**
		 * Which string to treat as null value.
		 */
		NULL_STRING("null.string"),
		
		/**
		 * Should invalid text lines be skipped.
		 */
		SKIP_INVALID_TEXT_LINE("skip.invalid"),;
		
		private final String name;
		
		private Key(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}

		public static Key parse(String name) {
			for(Key key : values()) {
				if(key.getName().equalsIgnoreCase(name)) {
					return key;
				}
			}
			throw new IllegalArgumentException("Unknown key '" + name + "'");
		}
	}
	
	public static final String DEFAULT_ISO_TIME_PATTERN 	 = "HH:mm:ss.SSS";
	public static final String DEFAULT_ISO_DATE_PATTERN 	 = "yyyy-MM-dd";
	public static final String DEFAULT_ISO_TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DEFAULT_SEPARATOR 			 = ",";
	public static final String DEFAULT_NULL_STRING 			 = "";
	public static final boolean DEFAULT_SKIP_INVALID_LINES	 = false;

	private final AtomicReference<String> datePattern;
	private final AtomicReference<String> timePattern;
	private final AtomicReference<String> timestampPattern;
	private final AtomicReference<String> separator;
	private final AtomicReference<String> nullString;
	private final AtomicBoolean skipInvalidLines;
	
	/**
	 * Uses default values.
	 */
	public Config() {
		this(DEFAULT_ISO_DATE_PATTERN, DEFAULT_ISO_TIME_PATTERN, 
				DEFAULT_ISO_TIMESTAMP_PATTERN, DEFAULT_SEPARATOR, 
					DEFAULT_NULL_STRING, DEFAULT_SKIP_INVALID_LINES);
	}

	public Config(String datePattern, String timePattern,
					String timestampPattern, String separator, 
						String nullString, boolean skipInvalidLines) {
		this.datePattern = new AtomicReference<String>(datePattern);
		this.timePattern = new AtomicReference<String>(timePattern);
		this.timestampPattern = new AtomicReference<String>(timestampPattern);
		this.separator = new AtomicReference<String>(separator);
		this.nullString = new AtomicReference<String>(nullString);
		this.skipInvalidLines = new AtomicBoolean(skipInvalidLines);
	}
	
	public <T> T get(Key key, Class<T> clazz) {
		return clazz.cast(get(key));
	}
	
	public Object get(Key key) {
		switch (key) {
			case DATE_PATTERN:
				return datePattern.get();
			case TIME_PATTERN:
				return timePattern.get();
			case TIMESTAMP_PATTERN:
				return timestampPattern.get();
			case SEPARATOR:
				return separator.get();
			case NULL_STRING:
				return nullString.get();
			case SKIP_INVALID_TEXT_LINE:
				return skipInvalidLines.get();
			default:
				throw new IllegalArgumentException("Bug in code, unexpected key '" + key + "'");
		}
	}
	
	public void set(Key key, Object value) {
		switch (key) {
			case DATE_PATTERN:
				setDatePattern((String)value);
				break;
			case TIME_PATTERN:
				setTimePattern((String)value);
				break;
			case TIMESTAMP_PATTERN:
				setTimestampPattern((String)value);
				break;
			case SEPARATOR:
				setSeparator((String)value);
				break;
			case NULL_STRING:
				setNullString((String)value);
				break;
			case SKIP_INVALID_TEXT_LINE:
				setSkipInvalidLines(Boolean.parseBoolean((String)value));
				break;
			default:
				throw new IllegalArgumentException("Bug in code, unexpected key '" + key + "'");
		}
	}

	public String getDatePattern() {
		return datePattern.get();
	}

	public String getTimePattern() {
		return timePattern.get();
	}

	public String getTimestampPattern() {
		return timestampPattern.get();
	}

	/**
	 * Separator in DSV text.
	 */
	public String getSeparator() {
		return separator.get();
	}

	public String getNullString() {
		return nullString.get();
	}

	public boolean skipInvalidLines() {
		return skipInvalidLines.get();
	}
	
	public void setDatePattern(String datePattern) {
		this.datePattern.set(datePattern);
	}
	
	public void setTimePattern(String timePattern) {
		this.timePattern.set(timePattern);
	}
	
	public void setTimestampPattern(String timestampPattern) {
		this.timestampPattern.set(timestampPattern);
	}
	
	public void setSeparator(String separator) {
		this.separator.set(separator);
	}
	
	public void setNullString(String nullString) {
		this.nullString.set(nullString);
	}
	
	public void setSkipInvalidLines(boolean skipInvalidLines) {
		this.skipInvalidLines.set(skipInvalidLines);
	}

	@Override
	public String toString() {
		return "Config [datePattern=" + datePattern + ", timePattern="
				+ timePattern + ", timestampPattern=" + timestampPattern
				+ ", separator=" + separator + ", nullString=" + nullString
				+ ", skipInvalidLines=" + skipInvalidLines + "]";
	}
}