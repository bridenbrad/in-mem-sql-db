/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc;

import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractDriver implements Driver {

	private static final int MAJOR = 1;
	private static final int MINOR = 0;

	@Override
	public final DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return new DriverPropertyInfo[] {};
	}

	@Override
	public final int getMajorVersion() {
		return MAJOR;
	}

	@Override
	public final int getMinorVersion() {
		return MINOR;
	}

	@Override
	public final boolean jdbcCompliant() {
		return false;
	}

	@Override
	public final Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}
}
