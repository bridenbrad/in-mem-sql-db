/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.split.SplittableRowIdSource;
import com.tirion.db.sql.exec.tuple.EmptyTuple;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.Tuples;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;
import com.tirion.executor.job.Job;
import com.tirion.executor.task.AbstractTask;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ParallelProjector extends AbstractProjector {
	
	private SplittableRowIdSource source;
	
	private boolean tasksStarted = false;
	private BlockingQueue<Tuple> queue;
	
	public void setSource(SplittableRowIdSource source) {
		this.source = source;
	}

	@Override
	public void init() {
//		queue = new LinkedBlockingQueue<Tuple>(job.getRuntime().configuration().getScanOperatorUnconsumedTuples());
	}

	@Override
	public void shutdown() {
	}

	@Override
	public Tuple next() {
		try {
			if(!tasksStarted()) {
				startTasks();
			}
			Tuple tuple = queue.take();
			if(tuple == EmptyTuple.EOS_TUPLE) {
				return null;
			}
			return tuple;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean tasksStarted() {
		return tasksStarted;
	}
	
	private void startTasks() throws Exception {
		final TupleProjectingMasterTask master = new TupleProjectingMasterTask(
				job, 
				valueExtractors.getFieldCount(), 
				valueExtractors, 
				new InternalTupleSink(), 
				source);
		
//		job.getRuntime().executor().submit(new ControlTask(job, master));
		tasksStarted = true;
	}
	
	private final class ControlTask extends AbstractTask<Void> {

		private final TupleProjectingMasterTask master;
		
		public ControlTask(Job job, TupleProjectingMasterTask master) {
			super(job);
			this.master = master;
		}

		@Override
		protected Void callInternal() throws Exception {
			master.call();
			queue.put(EmptyTuple.EOS_TUPLE);
			return null;
		}		
	}
	
	private final class InternalTupleSink implements TupleListener {

		@Override
		public void onTuples(Tuples tuples) {
			for(Tuple tuple : tuples.get()) {
				onTuple(tuple);
			}
		}
		
		@Override
		public void onTuple(Tuple tuple) {
			try {
				queue.put(tuple);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
