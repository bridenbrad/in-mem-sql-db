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
import java.util.concurrent.Future;

import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.ValueExtractors;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.BatchRowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.split.SplittableRowIdSource;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TupleProjectingMasterTask extends TupleProjectingTask {

	private final SplittableRowIdSource rowIdSource;
	
	public TupleProjectingMasterTask(Job job, int fieldCount, ValueExtractors extractors,
			TupleListener sink, SplittableRowIdSource rowIdSource) {
		super(job, fieldCount, sink, extractors);
		this.rowIdSource = rowIdSource;
	}

	@Override
	protected Void callInternal() throws Exception {
////		int tuplesPerTask = getRuntime().configuration().getMinTuplesPerTupleProjectorSlaveTask();
////		int taskCount = rowIdSource.size() / tuplesPerTask;
//		if(taskCount == 0) {
//			taskCount = 1;
//		}
//		List<BatchRowIdSource> sources = rowIdSource.splitBatch(taskCount);
//		taskCount = sources.size();
//		
//		List<TupleProjectingSlaveTask> tasks = new ArrayList<TupleProjectingSlaveTask>(taskCount);
//		for (int i = 0; i < taskCount; i++) {
//			TupleProjectingSlaveTask task = new TupleProjectingSlaveTask(
//					getJob(), 
//					fieldCount, 
//					extractors, 
//					sink, 
//					sources.get(i), 
//					getRuntime().configuration().getBatchSizeForTupleProjectorSlaveTask());
//			tasks.add(task);
//		}
//		List<Future<Void>> futures = getRuntime().executor().submit(tasks);
//		for(Future<Void> future : futures) {
//			future.get();
//		}
		return null;
	}
}
