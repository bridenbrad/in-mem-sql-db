/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.jdbc.impl;

import java.sql.SQLException;

import com.tirion.db.jdbc.AbstractResultSet;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class SingleRowResultSet extends AbstractResultSet {

	private boolean done = false;
	
	@Override
	public final boolean next() throws SQLException {
		if(done) {
			return false;
		}
		done = true;
		return true;
	}

	@Override
	public final void close() throws SQLException {
	}
}
