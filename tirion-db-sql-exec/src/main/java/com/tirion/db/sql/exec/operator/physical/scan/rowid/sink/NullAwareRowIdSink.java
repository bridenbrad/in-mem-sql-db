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

import com.tirion.db.sql.exec.operator.physical.scan.filter.page.PageFilter;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.EmptyRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.ListBackedRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
import com.tirion.db.store.page.Page;

/**
 * Used during execution of {@link PageFilter}s. If page filter is doing
 * brute force scan, then we skip row IDs which are null via this {@link RowIdSink}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NullAwareRowIdSink extends DeletedRowsAwareRowIdSink implements PageAwareRowIdSink {

	private Page page;
	private final List<Long> rowIds = new LinkedList<Long>();

	@Override
	public final void before(Page page) {
		this.page = page;
	}

	@Override
	public final void after(Page page) {
		page = null;
	}	

	@Override
	protected final void onExistingRow(long rowId) {
		if(!page.isNull(rowId)) {
			onNonNullValue(rowId);
		}
	}
	
	private void onNonNullValue(long rowId) {
		rowIds.add(rowId);
	}

	@Override
	public RowIdSource asRowIdSource() {
		if (rowIds.isEmpty()) {
			return new EmptyRowIdSource();
		}
		return new ListBackedRowIdSource(rowIds);
	}
}
