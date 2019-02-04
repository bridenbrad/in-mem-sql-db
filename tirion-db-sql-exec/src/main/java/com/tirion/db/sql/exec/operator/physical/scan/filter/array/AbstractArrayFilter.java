/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter.array;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.RowIdSink;

/**
 * Has {@link RowIdSink} and starting row ID.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractArrayFilter implements ArrayFilter {

	private final RowIdSink rowIdSink;
	private final long startRowId;
	
	public AbstractArrayFilter(RowIdSink rowIdSink, long startRowId) {
		super();
		this.rowIdSink = rowIdSink;
		this.startRowId = startRowId;
	}

	protected final RowIdSink getRowIdSink() {
		return rowIdSink;
	}
	
	protected final long getStartRowId() {
		return startRowId;
	}
}
