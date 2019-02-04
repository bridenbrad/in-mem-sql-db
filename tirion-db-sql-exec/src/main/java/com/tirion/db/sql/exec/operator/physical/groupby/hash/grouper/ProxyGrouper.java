/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.tirion.common.NotYetImplementedException;
import com.tirion.db.sql.exec.operator.physical.common.key.Key;
import com.tirion.db.sql.exec.tuple.EmptyTuple;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.executor.job.Job;
import com.tirion.executor.task.AbstractTask;
import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ProxyGrouper implements Grouper {

	private final Grouper underlying;
	
	private final BlockingQueue<Entry> queue = new LinkedBlockingQueue<Entry>();
	private final Future<LinkedList<Tuple>> future;
	
	public ProxyGrouper(Grouper underlying, Job job) {
		super();
		this.underlying = underlying;
//		future = job.getRuntime().executor().submit(new DequeueTask(job));
		throw new NotYetImplementedException();
	}

	@Override
	public Statistics getStatistics() {
		return underlying.getStatistics();
	}

	@Override
	public void onTuple(Tuple tuple, Key key) {
		queue.add(new Entry(key, tuple));
	}
	
	@Override
	public LinkedList<Tuple> getResult() {
		try {
			return future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	private final class DequeueTask extends AbstractTask<LinkedList<Tuple>> {

		public DequeueTask(Job job) {
			super(job);
		}

		@Override
		protected LinkedList<Tuple> callInternal() throws Exception {
			consumeInputTuples();
			return buildOutputTuples();
		}
		
		private void consumeInputTuples() throws Exception {
			final int bufferSize = 256; // TODO externally configurable
			List<Entry> buffer = new ArrayList<Entry>(bufferSize);
			while(true) {			
				int count = queue.drainTo(buffer, bufferSize);
				if(count > 0) {
					for (int i = 0; i < count; i++) {
						deliver(buffer.get(i));
					}
					buffer.clear();
				} else {					
					Entry entry = queue.take();
					if(entry.tuple == EmptyTuple.EOS_TUPLE) {
						break;
					} else {		
						deliver(entry);
					}
				}
			}
		}
		
		private void deliver(Entry entry) {
			underlying.onTuple(entry.tuple, entry.key);
		}
		
		private LinkedList<Tuple> buildOutputTuples() {
			return underlying.getResult();
		}
	}

	private static final class Entry {
		
		private final Key key;
		private final Tuple tuple;
		
		private Entry(Key key, Tuple tuple) {
			super();
			this.key = key;
			this.tuple = tuple;
		}
	}
}
