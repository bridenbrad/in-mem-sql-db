/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;

import java.util.LinkedList;
import java.util.List;

import com.tirion.common.Util;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class PageBoundaryAwareRowIdSource implements BatchRowIdSource {

	private final RowIdSource source;
	private Long rowId;
	
	public PageBoundaryAwareRowIdSource(RowIdSource source) {
		super();
		this.source = source;
	}

	@Override
	public boolean isEmpty() {
		return source.isEmpty();
	}

	@Override
	public boolean hasNext() {
		return source.hasNext();
	}

	@Override
	public List<Long> nextBatch() {
		if(rowId == null) {
			rowId = source.next();
		}
		List<Long> rowIds = new LinkedList<Long>();
		rowIds.add(rowId);
		while(source.hasNext()) {
			Long newRowId = source.next();
			if(Util.areOnSamePage(rowId, newRowId)) {
				rowIds.add(newRowId);
			} else {
				rowId = newRowId;
				break;
			}
		}
		return rowIds;
	}
	
	@Override
	public List<Long> asList() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public long next() {
		throw new UnsupportedOperationException();
	}
}
