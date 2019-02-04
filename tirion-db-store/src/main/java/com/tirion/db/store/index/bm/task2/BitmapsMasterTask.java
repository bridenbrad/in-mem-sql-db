/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.index.bm.task2;

import java.util.LinkedList;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.executor.job.Job;
import com.tirion.executor.task.AbstractTask;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BitmapsMasterTask extends AbstractTask<Bitmaps> {

	private final int maxPageId;
	private final LinkedList<Bitmaps> bitmaps;
	
	public BitmapsMasterTask(Job job, int maxPageId, LinkedList<Bitmaps> bitmaps) {
		super(job);
		this.maxPageId = maxPageId;
		this.bitmaps = bitmaps;
	}

	@Override
	protected final Bitmaps callInternal() throws Exception {
		throw new NotYetImplementedException();
//		while(bitmaps.size() > 1) {			
//			int verticals = bitmaps.size() / 2;
//			int horizontals = maxPageId / getRuntime().configuration().getMinPageCountForAndBitmapTask();
//			if(horizontals == 0) {
//				horizontals = 1;
//			} else if(maxPageId % getRuntime().configuration().getMinPageCountForAndBitmapTask() != 0) {
//				++horizontals;
//			}
//			int totalTaskCount = verticals * horizontals;
//			
//			List<AbstractTask<Bitmaps>> tasks = new ArrayList<AbstractTask<Bitmaps>>(totalTaskCount);
//			for (int i = 0; i < verticals; i++) {				
//				Bitmaps first = bitmaps.removeFirst();
//				Bitmaps second = bitmaps.removeFirst();
//				int start = 0;
//				int end = Math.min(maxPageId, getRuntime().configuration().getMinPageCountForAndBitmapTask());
//				for (int j = 0; j < horizontals; j++) {
//					AbstractTask<Bitmaps> task = buildTask(start, end, first, second);
//					tasks.add(task);
//					start = end;
//					end = Math.min(maxPageId, end + getRuntime().configuration().getMinPageCountForAndBitmapTask());
//				}
//			}			
//			
//			List<Future<Bitmaps>> futures = new ArrayList<Future<Bitmaps>>();
//			for(AbstractTask<Bitmaps> task : tasks) {
//				futures.add(getRuntime().executor().submit(task));
//			}
//			for(Future<Bitmaps> future : futures) {
//				bitmaps.add(future.get());
//			}
//		}
//		return bitmaps.removeFirst();
	}
	
	protected abstract AbstractTask<Bitmaps> buildTask(int start, int end, Bitmaps first, Bitmaps second);
	
	public static final class OrEwahBitmapsMasterTask extends BitmapsMasterTask {

		public OrEwahBitmapsMasterTask(Job job, int maxPageId, LinkedList<Bitmaps> bitmaps) {
			super(job, maxPageId, bitmaps);
		}

		@Override
		protected AbstractTask<Bitmaps> buildTask(int start, int end, Bitmaps first, Bitmaps second) {
			return new OrEwahBitmapsSlaveTask(getJob(), start, end, first, second);
		}
	}
	
	public static final class AndEwahBitmapsMasterTask extends BitmapsMasterTask {

		public AndEwahBitmapsMasterTask(Job job, int maxPageId, LinkedList<Bitmaps> bitmaps) {
			super(job, maxPageId, bitmaps);
		}

		@Override
		protected AbstractTask<Bitmaps> buildTask(int start, int end, Bitmaps first, Bitmaps second) {
			return new AndEwahBitmapsSlaveTask(getJob(), start, end, first, second);
		}
	}
}
