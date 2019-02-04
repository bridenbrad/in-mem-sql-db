/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.sink;

import java.util.LinkedList;
import java.util.List;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.EmptyRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.ListBackedRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;

/**
 * Thin wrapper around Java's {@link LinkedList}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleRowIdSink implements RowIdSink {

	private final List<Long> rowIds = new LinkedList<Long>();
	
	public List<Long> getRowIds() {
		return rowIds;
	}

	@Override
	public void onRowId(long rowId) {
		rowIds.add(rowId);
	}

	@Override
	public void onRowIdRange(long start, long end) {
		for (long i = start; i < end; i++) {
			onRowId(i);
		}
	}
	
	@Override
	public RowIdSource asRowIdSource() {
		if (rowIds.isEmpty()) {
			return new EmptyRowIdSource();
		}
		return new ListBackedRowIdSource(rowIds);
	}
}
