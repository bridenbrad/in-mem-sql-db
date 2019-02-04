/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.sink;

import java.util.Set;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class DeletedRowsAwareRowIdSink implements RowIdSink {

	private Set<Long> deletedRowIds;

	public final void setDeletedRowIds(Set<Long> deletedRowIds) {
		this.deletedRowIds = deletedRowIds;
	}

	@Override
	public final void onRowId(long rowId) {
		if(deletedRowIds == null || !deletedRowIds.contains(rowId)) {
			onExistingRow(rowId);
		}
	}

	@Override
	public final void onRowIdRange(long start, long end) {
		for (long i = start; i < end; i++) {
			onRowId(i);
		}
	}
	
	/**
	 * Call guarantees that row exists.
	 */
	protected abstract void onExistingRow(long rowId);
}
