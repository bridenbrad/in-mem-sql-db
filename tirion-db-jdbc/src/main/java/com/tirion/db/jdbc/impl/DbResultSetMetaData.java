/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.sql.SQLException;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.jdbc.AbstractResultSetMetaData;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DbResultSetMetaData extends AbstractResultSetMetaData {
	
	private final Entity entity;
	
	public DbResultSetMetaData(Entity entity) {
		super();
		this.entity = entity;
	}

	@Override
	public int getColumnCount() throws SQLException {
		return entity.fieldCount();
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		return entity.getField(column-1).getName();
	}

	@Override
	public String getColumnName(int column) throws SQLException {
		return getColumnLabel(column);
	}

	@Override
	public int getColumnType(int column) throws SQLException {
		return entity.getField(column-1).getDeclaredType().getJdbcType();
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		return entity.getField(column-1).getDeclaredType().getJdbcTypeName();
	}
}
