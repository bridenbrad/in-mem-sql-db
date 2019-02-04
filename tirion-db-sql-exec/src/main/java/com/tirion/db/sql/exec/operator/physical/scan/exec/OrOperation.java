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

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class OrOperation extends CompositeOperation {

	public OrOperation(Job job) {
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
			filter = merger.mergeOr(filter, source);
		}
		
		return filter;
	}

	private RowIdSource executeIndexed() {
		Bitmaps bitmaps = super.executeIndexedOperations(false);
		if(bitmaps == null || bitmaps.isEmpty()) {
			return null;
		}
//		return new BitmapsBackedRowIdSource(bitmaps);
		throw new NotYetImplementedException();
	}
	
	private RowIdSource executeNonIndexed(RowIdSource filter) {
//		List<RowIdSource> sources = new ArrayList<RowIdSource>(nonIndexed.size() + 1);
//		sources.add(filter);
//		for(NonIndexedOperation op : nonIndexed) {
//			filter = op.execute(null);
//			if(filter != null && !filter.isEmpty()) {
//				sources.add(filter);
//			}
//		}
//		return merger.mergeOr(sources.toArray(new RowIdSource[]{}));
		throw new NotYetImplementedException();
	}
	
	private RowIdSource executeComposite() {		
		List<RowIdSource> sources = new ArrayList<RowIdSource>(composite.size());
		for(CompositeOperation op : composite) {
			RowIdSource filter = op.execute();
			if(filter != null && !filter.isEmpty()) {
				sources.add(filter);
			}
		}
		return merger.mergeOr(sources.toArray(new RowIdSource[]{}));
	}
}
