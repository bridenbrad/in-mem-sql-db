/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.jdbc.AbstractResultSet;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DbResultSet extends AbstractResultSet {

	private final Entity entity;

	private boolean isClosed = false;
	
	private int rowIndex = -1;
	private List<Object[]> list;
	private Object[] tuple;
	
	public DbResultSet(Statement statement, Entity entity, List<Object[]> list) {
		super(statement);
		this.entity = entity;
		this.list = list;
	}
	
	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return new DbResultSetMetaData(entity);
	}

	@Override
	public boolean next() throws SQLException {
		++rowIndex;
		if(rowIndex < list.size()) {
			tuple = list.get(rowIndex);
			return true;
		}
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return isClosed;
	}

	@Override
	public void close() throws SQLException {
		isClosed = true;
	}
	
	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		Object value = tuple[columnIndex-1];
		if(value == null) {
			return false;
		}
		return ((Boolean)value).booleanValue();
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		Object value = tuple[columnIndex-1];
		if(value == null) {
			return 0;
		}
		return ((Byte)value).byteValue();
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		Object value = tuple[columnIndex-1];
		if(value == null) {
			return 0;
		}
		return ((Short)value).shortValue();
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		Object value = tuple[columnIndex-1];
		if(value == null) {
			return 0;
		}
		return ((Integer)value).intValue();
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		Object value = tuple[columnIndex-1];
		if(value == null) {
			return 0;
		}
		return ((Long)value).longValue();
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		Object value = tuple[columnIndex-1];
		if(value == null) {
			return 0;
		}
		return ((Float)value).floatValue();
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		Object value = tuple[columnIndex-1];
		if(value == null) {
			return 0;
		}
		return ((Double)value).doubleValue();
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		return (String) tuple[columnIndex-1];
	}
	
	@Override
	public String getString(String columnLabel) throws SQLException {
		return getString(entity.getField(columnLabel).getIndex() + 1);
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		return getBoolean(entity.getField(columnLabel).getIndex() + 1);
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		return getByte(entity.getField(columnLabel).getIndex() + 1);
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		return getShort(entity.getField(columnLabel).getIndex() + 1);
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		return getInt(entity.getField(columnLabel).getIndex() + 1);
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		return getLong(entity.getField(columnLabel).getIndex() + 1);
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		return getFloat(entity.getField(columnLabel).getIndex() + 1);
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return getDouble(entity.getField(columnLabel).getIndex() + 1);
	}
	
	@Override
	public int getType() throws SQLException {
		return ResultSet.TYPE_FORWARD_ONLY;
	}
}
