/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractResultSet implements ResultSet {
	
	private final Statement statement;
	
	public AbstractResultSet() {
		this(null);
	}

	public AbstractResultSet(Statement statement) {
		super();
		this.statement = statement;
	}

	@Override
	public final <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public final boolean wasNull() throws SQLException {
		return false;
	}

	@Override
	public final BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final byte[] getBytes(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Date getDate(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Time getTime(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Timestamp getTimestamp(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final InputStream getAsciiStream(int columnIndex)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final InputStream getUnicodeStream(int columnIndex)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final InputStream getBinaryStream(int columnIndex)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final BigDecimal getBigDecimal(String columnLabel, int scale)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final byte[] getBytes(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Date getDate(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Time getTime(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Timestamp getTimestamp(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final InputStream getAsciiStream(String columnLabel)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final InputStream getUnicodeStream(String columnLabel)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final InputStream getBinaryStream(String columnLabel)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public final void clearWarnings() throws SQLException {
	}

	@Override
	public final String getCursorName() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Object getObject(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Object getObject(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final int findColumn(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Reader getCharacterStream(String columnLabel)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final BigDecimal getBigDecimal(String columnLabel)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean isBeforeFirst() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean isAfterLast() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean isFirst() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean isLast() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void beforeFirst() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void afterLast() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean first() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean last() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final int getRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean absolute(int row) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean relative(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean previous() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void setFetchDirection(int direction) throws SQLException {
	}

	@Override
	public final int getFetchDirection() throws SQLException {
		return ResultSet.FETCH_FORWARD;
	}

	@Override
	public final void setFetchSize(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final int getFetchSize() throws SQLException {
		return 100;
	}

	@Override
	public final int getConcurrency() throws SQLException {
		return ResultSet.CONCUR_READ_ONLY;
	}

	@Override
	public final boolean rowUpdated() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNull(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBoolean(int columnIndex, boolean x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateShort(int columnIndex, short x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateInt(int columnIndex, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateLong(int columnIndex, long x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateDouble(int columnIndex, double x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateString(int columnIndex, String x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBytes(int columnIndex, byte[] x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateAsciiStream(int columnIndex, InputStream x,
			int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBinaryStream(int columnIndex, InputStream x,
			int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateCharacterStream(int columnIndex, Reader x,
			int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateObject(int columnIndex, Object x, int scaleOrLength)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateObject(int columnIndex, Object x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNull(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBoolean(String columnLabel, boolean x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateByte(String columnLabel, byte x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateShort(String columnLabel, short x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateInt(String columnLabel, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateLong(String columnLabel, long x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateFloat(String columnLabel, float x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateDouble(String columnLabel, double x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBigDecimal(String columnLabel, BigDecimal x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateString(String columnLabel, String x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBytes(String columnLabel, byte[] x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateDate(String columnLabel, Date x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateTime(String columnLabel, Time x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateTimestamp(String columnLabel, Timestamp x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateAsciiStream(String columnLabel, InputStream x,
			int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBinaryStream(String columnLabel, InputStream x,
			int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateCharacterStream(String columnLabel, Reader reader,
			int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateObject(String columnLabel, Object x,
			int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateObject(String columnLabel, Object x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void insertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void deleteRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void refreshRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Statement getStatement() throws SQLException {
		return statement;
	}

	@Override
	public final Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Date getDate(int columnIndex, Calendar cal)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Date getDate(String columnLabel, Calendar cal)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Time getTime(int columnIndex, Calendar cal)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Time getTime(String columnLabel, Calendar cal)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Timestamp getTimestamp(String columnLabel, Calendar cal)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBlob(String columnLabel, Blob x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateClob(String columnLabel, Clob x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateArray(String columnLabel, Array x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateRowId(String columnLabel, RowId x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNString(int columnIndex, String nString)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNString(String columnLabel, String nString)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNClob(int columnIndex, NClob nClob)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Reader getNCharacterStream(int columnIndex)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final Reader getNCharacterStream(String columnLabel)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNCharacterStream(int columnIndex, Reader x,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateAsciiStream(int columnIndex, InputStream x,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBinaryStream(int columnIndex, InputStream x,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateCharacterStream(int columnIndex, Reader x,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateAsciiStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBlob(int columnIndex, InputStream inputStream,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateClob(int columnIndex, Reader reader)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNClob(int columnIndex, Reader reader)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final <T> T getObject(int columnIndex, Class<T> type)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public final <T> T getObject(String columnLabel, Class<T> type)
			throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean next() throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public int getType() throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new IllegalStateException("This method should be overridden");
	}
}
