/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.ws.Response;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.jdbc.AbstractStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DbStatement extends AbstractStatement {
	
	private final DbConnection connection;
	private boolean isClosed = false;
	private ResultSet resultSet;

	public DbStatement(DbConnection connection) {
		super();
		this.connection = connection;
	}
	
	@Override
	public boolean execute(String sql) throws SQLException {
		if(sql.startsWith("SELECT")) {
			resultSet = executeQuery(sql);
		} else {
			executeUpdate(sql);
		}
		return true;
	}
	
	@Override
	public boolean getMoreResults() throws SQLException {
		return resultSet != null;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		ResultSet result = resultSet;
		resultSet = null;
		return result;
	}

	@Override
	public synchronized ResultSet executeQuery(String sql) throws SQLException {
//		BatchSelectResponse response = (BatchSelectResponse) executeInternal(sql);
//		return new DbResultSet(this, response.getEntity(), response.getTuples());
		throw new NotYetImplementedException();
	}

	@Override
	public synchronized int executeUpdate(String sql) throws SQLException {
		executeInternal(sql);
		return 0;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		return connection;
	}

	@Override
	public synchronized boolean isClosed() throws SQLException {
		return isClosed;
	}
	
	@Override
	public synchronized void close() throws SQLException {
		isClosed = true;
	}
	
	private Response executeInternal(String sql) {
		throw new NotYetImplementedException();
//		try {
//			TextRequest request = new TextRequest(null, sql);
//			BatchSelectResponse response = (BatchSelectResponse) connection.getJdbcServer().execute(request);
//			if(response.getStatus() != Status.OK) {
//				throw new SQLException(response.getMessage(), response.getException());
//			}
//			return response;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
	}
}
