/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.type;

import java.sql.Types;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public enum Type {

	BOOLEAN("boolean", Boolean.class, "Boolean", 1, Types.BOOLEAN, "BIT", true),
	BYTE("byte", Byte.class, "Byte", 1, Types.TINYINT, "TINYINT", true),
	SHORT("short", Short.class, "Short", 2, Types.SMALLINT, "SMALLINT", true),
	INT("int", Integer.class, "Int", 4, Types.INTEGER, "INTEGER", true),
	LONG("long", Long.class, "Long", 8, Types.BIGINT, "BIGINT", true),
	FLOAT("float", Float.class, "Float", 4, Types.REAL, "REAL", true),
	DOUBLE("double", Double.class, "Double", 8, Types.DOUBLE, "DOUBLE", true),
	// derived types
	VARCHAR("String", String.class, "<string>", 8, Types.VARCHAR, "VARCHAR", false),
	TIMESTAMP("timestamp", Long.class, "<timestamp>", 8, Types.TIMESTAMP,"TIMESTAMP", false),
	TIME("time", Integer.class, "<time>", 4, Types.TIME, "TIME", false),
	DATE("date", Short.class, "<date>", 2, Types.DATE, "DATE", false),
	;
	
	private final String smallTypeName;
	private final Class<?> clazz;
	private final String typeName;	
	private final int width;
	private final int jdbcType;
	private final String jdbcTypeName;
	private final boolean isNative;
	
	private Type(String smallTypeName, Class<?> clazz, String typeName,
			int width, int jdbcType, String jdbcTypeName, boolean isNative) {
		this.smallTypeName = smallTypeName;
		this.clazz = clazz;
		this.typeName = typeName;
		this.width = width;
		this.jdbcType = jdbcType;
		this.jdbcTypeName = jdbcTypeName;
		this.isNative = isNative;
	}

	/**
	 * Number of bytes per value.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Boxed name used in JDK APIs i.e. Int such
	 * as IntBuffer.
	 */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * Java primitive name i.e. int.
	 */
	public String getSmallTypeName() {
		return smallTypeName;
	}
	
	/**
	 * Java boxed name i.e. Integer.
	 */
	public String getLargeTypeName() {
		return clazz.getSimpleName();
	}

	/**
	 * Underlying class used to represent type.
	 */
	public Class<?> getClazz() {
		return clazz;
	}
	
	public int getJdbcType() {
		return jdbcType;
	}
	
	public String getJdbcTypeName() {
		return jdbcTypeName;
	}
	
	public boolean isNative() {
		return isNative;
	}

	public static Type parseFromString(String str) {
		for(Type type : values()) {
			if(type.name().equalsIgnoreCase(str)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unable to resolve '" + str + "' to java type");
	}
	
	public static boolean byteToBoolean(byte value) {
		return value == 0 ? false : true;
	}
	
	public static byte booleanToByte(boolean value) {
		return value ? (byte) 1 : (byte) 0;
	}
}
