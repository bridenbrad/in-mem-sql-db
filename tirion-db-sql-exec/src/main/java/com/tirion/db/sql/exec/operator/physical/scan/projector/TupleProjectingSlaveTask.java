/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector;

import java.util.ArrayList;
import java.util.List;

import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.ValueExtractors;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.BatchRowIdSource;
import com.tirion.db.sql.exec.tuple.OnHeapTuple;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.Tuples;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TupleProjectingSlaveTask extends TupleProjectingTask {

	private final BatchRowIdSource rowIdSource;
	private final int batchSize;
	
	public TupleProjectingSlaveTask(Job job, int fieldCount, ValueExtractors extractors,
			TupleListener sink, BatchRowIdSource rowIdSource, int batchSize) {
		super(job, fieldCount, sink, extractors);
		this.rowIdSource = rowIdSource;
		this.batchSize = batchSize;
	}

	@Override
	protected Void callInternal() throws Exception {
		Tuples tups = new Tuples();
		while(true) {
			if(!rowIdSource.hasNext()) {
				break;
			}
			List<Long> rowIds = rowIdSource.nextBatch();
			List<Tuple> tuples = new ArrayList<Tuple>(rowIds.size());
			for (int i = 0; i < rowIds.size(); i++) {
				tuples.add(new OnHeapTuple(fieldCount));
			}
			extractors.exec(rowIds, tuples);
			
			// deliver tuples
			if(tuples.size() >= batchSize) {
				sink.onTuples(new Tuples(tuples));
			} else {
				tups.append(tuples);	
				if(tups.getCount() >= batchSize) {					
					sink.onTuples(tups);
					tups = new Tuples();
				}
			}
		}
		if(!tups.isEmpty()) {
			sink.onTuples(tups);
		}
		return null;
	}
}
