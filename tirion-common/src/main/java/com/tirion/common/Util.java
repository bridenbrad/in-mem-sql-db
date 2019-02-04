/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;

import com.tirion.common.file.FileUtil;
import com.tirion.common.Constants;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class Util {
	
	public static boolean areOnSamePage(long rowId1, long rowId2) {
		final long start = (rowId1 / Constants.ROWS_PER_PAGE) * Constants.ROWS_PER_PAGE;
		if(rowId2 < (start + Constants.ROWS_PER_PAGE)) {
			return true;
		}
		return false;
	}
	
	public static Properties loadProps(String resourceName) {
		InputStream is = null;
		try {
			Properties props = new Properties();
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
			if(is == null) {
				throw new IllegalArgumentException("Unable to load resource '" + resourceName + "' from classpath");
			}	
			props.load(is);
			return props;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			FileUtil.close(is);
		}
	}
	
	public static boolean hasLength(String str) {
		return str != null && !str.isEmpty();
	}
	
	public static void closeJdbc(ResultSet rs, Statement stmt, Connection conn) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public static void assertTrue(boolean expression) {
		if(!expression) {
			throw new IllegalArgumentException("Expected true but found false");
		}
	}
	
	public static void assertTrue(boolean expression, String errorMessage) {
		if(!expression) {
			throw new IllegalArgumentException("Assert failed with message '" + errorMessage + "'");
		}
	}

	public static void assertNotNull(Object object) {
		if(object == null) {
			throw new NullPointerException();
		}
	}
	
	public static String concatenate(String...strings) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			buffer.append(strings[i]);
		}
		return buffer.toString();
	}
	
	public static UUID getUuid() {
		synchronized (Util.class) {
			return UUID.randomUUID();
		}
	}
	
	/**
	 * Remove any trailing commas or dots.
	 */
	public static String trimCommasDots(String str) {
		while(!str.isEmpty()) {
			char last = str.charAt(str.length()-1);
			if(last == ',' || last == '.') {
				str = str.substring(0, str.length() - 1);
				continue;
			}
			break;
		}
		return str;
	}
	
	/**
	 * Remove all leading and trailing " and ' chars.
	 */
	public static String trimQuotes(String str) {
		while(!str.isEmpty()) {
			char first = str.charAt(0);
			if(first == '"' || first == '\'') {				
				str = str.substring(1);
				continue;
			}
			char last = str.charAt(str.length()-1);
			if(last == '"' || last == '\'') {
				str = str.substring(0, str.length() - 1);
				continue;
			}
			break;
		}
		return str;
	}
	
	/**
	 * Convert package name to file system path
	 */
	public static String asFileSystemPath(String packageName) {
		return packageName.replaceAll("\\.", File.separator) + File.separator;
	}
	
	private Util() {
	}
}
