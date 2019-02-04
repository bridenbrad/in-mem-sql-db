/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.tirion.db.jdbc.AbstractResultSet;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DbEmptyResultSet extends AbstractResultSet {
	
	public DbEmptyResultSet() {
		super(null);
	}

	@Override
	public boolean next() throws SQLException {
		return false;
	}

	@Override
	public void close() throws SQLException {
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return null;
	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return true;
	}
}
