/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractResultSetMetaData implements ResultSetMetaData {

	@Override
	public final <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public final boolean isAutoIncrement(int column) throws SQLException {
		return false;
	}

	@Override
	public final boolean isCaseSensitive(int column) throws SQLException {
		return true;
	}

	@Override
	public final boolean isSearchable(int column) throws SQLException {
		return true;
	}

	@Override
	public final boolean isCurrency(int column) throws SQLException {
		return false;
	}

	@Override
	public final int isNullable(int column) throws SQLException {
		return ResultSetMetaData.columnNullable;
	}

	@Override
	public final boolean isSigned(int column) throws SQLException {
		return true;
	}

	@Override
	public final int getColumnDisplaySize(int column) throws SQLException {
		return 10;
	}

	@Override
	public final String getSchemaName(int column) throws SQLException {
		return "admin";
	}

	@Override
	public final int getPrecision(int column) throws SQLException {
		return 2;
	}

	@Override
	public final int getScale(int column) throws SQLException {
		return 2;
	}

	@Override
	public final String getTableName(int column) throws SQLException {
		return "";
	}

	@Override
	public final String getCatalogName(int column) throws SQLException {
		return "default";
	}

	@Override
	public final boolean isReadOnly(int column) throws SQLException {
		return true;
	}

	@Override
	public final boolean isWritable(int column) throws SQLException {
		return false;
	}

	@Override
	public final boolean isDefinitelyWritable(int column) throws SQLException {
		return false;
	}

	@Override
	public final String getColumnClassName(int column) throws SQLException {
		throw new UnsupportedOperationException();
	}
}
