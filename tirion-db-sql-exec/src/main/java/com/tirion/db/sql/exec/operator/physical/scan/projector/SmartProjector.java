/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.tirion.common.Util;
import com.tirion.db.sql.exec.operator.physical.scan.ScanOperator;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
import com.tirion.db.sql.exec.tuple.OnHeapTuple;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SmartProjector extends AbstractProjector {
	
	private RowIdSource rowIdSource;
	private List<Tuple> cache = new LinkedList<Tuple>();

	/**
	 * Should be called by {@link ScanOperator}.
	 */
	public final void setRowIdSource(RowIdSource rowIdSource) {
		this.rowIdSource = rowIdSource;
	}
	
	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
		cache = null;
	}

	@Override
	public Tuple next() {
		if(!cache.isEmpty()) {
			return cache.remove(0);
		}
		if(!rowIdSource.hasNext()) {
			return null;
		}
		List<Long> rowIds = new LinkedList<Long>();
		outer:
		while(rowIdSource.hasNext()) {
			final long rowId = rowIdSource.next();
			rowIds.add(rowId);
			while(rowIdSource.hasNext()) {
				final long nextRowId = 1;// rowIdSource.peek();				
				if(Util.areOnSamePage(rowId, nextRowId)) {
					rowIds.add(nextRowId);
					rowIdSource.next();
				} else {
					break outer;
				}
			}
		}
		if(!rowIds.isEmpty()) {
			cache.addAll(extract(rowIds));
		}
		return next();
	}
	
	private List<Tuple> extract(List<Long> rowIds) {
		Tuple[] array = new Tuple[rowIds.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = new OnHeapTuple(valueExtractors.getFieldCount());
		}
		List<Tuple> tuples = Arrays.asList(array);
		valueExtractors.exec(rowIds, tuples);
		return tuples;
	}
}
