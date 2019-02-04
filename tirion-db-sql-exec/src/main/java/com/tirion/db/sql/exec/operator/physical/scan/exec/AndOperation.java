/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.exec;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.bitmap.Bitmaps;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.BitmapsBackedRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
import com.tirion.executor.job.Job;

/**
 * All operations under AND operation are divided into 3 groups:
 * indexed compares, non-indexed compares & composite operations.
 * Indexed compares are executed first, followed by non-indexed
 * compares followed by composite ones.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class AndOperation extends CompositeOperation {
	
	public AndOperation(Job job) {
		super(job);
	}

	@Override
	public RowIdSource execute() {
		// execute indexed
		RowIdSource filter = null;
		if(hasIndexed()) {
			filter = executeIndexed();
		}
		
		// execute non-indexed
		if(hasNonIndexed()) {
			filter = executeNonIndexed(filter);
		}
		
		// execute composite
		if(hasComposite()) {
			RowIdSource source = executeComposite();
			filter = merger.mergeAnd(filter, source);
		}
		
		return filter;
	}

	private RowIdSource executeIndexed() {
		Bitmaps bitmaps = super.executeIndexedOperations(true);
		if(bitmaps == null || bitmaps.isEmpty()) {
			return null;
		}
		return new BitmapsBackedRowIdSource(bitmaps);
	}
	
	private RowIdSource executeNonIndexed(RowIdSource filter) {
//		for(NonIndexedOperation op : nonIndexed) {
//			filter = op.execute(filter);
//			if(filter == null) {
//				return null;
//			}
//		}
		return filter;
	}
	
	private RowIdSource executeComposite() {		
		List<RowIdSource> sources = new ArrayList<RowIdSource>(composite.size());
		for(CompositeOperation op : composite) {
			RowIdSource filter = op.execute();
			if(filter == null || filter.isEmpty()) {
				return null;
			}
			sources.add(filter);
		}
		return merger.mergeAnd(sources.toArray(new RowIdSource[]{}));
	}
}
