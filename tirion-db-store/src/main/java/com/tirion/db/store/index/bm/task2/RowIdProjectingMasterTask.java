/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.store.index.bm.task2;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import com.tirion.common.bitmap.Bitmaps;
//import com.tirion.executor.callback.LatchCallback;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class RowIdProjectingMasterTask extends RowIdProjectingTask<RowIdSource> {
//	
//	public RowIdProjectingMasterTask(Job job, Bitmaps bitmaps) {
//		super(job, bitmaps);
//	}
//
//	@Override
//	protected RowIdSource callInternal() throws Exception {	
//		long rowId = 0;
//		int start = 0;
//		int end = Math.min(bitmaps.getCount(), getRuntime().configuration().getMinPageCountForRowIdProjectingSlaveTask());
//		
//		List<SimpleRowIdSink> sinks = new LinkedList<SimpleRowIdSink>();
//		List<RowIdProjectingSlaveTask> tasks = new LinkedList<RowIdProjectingSlaveTask>();
//		
//		while(start < bitmaps.getCount()) {
//			SimpleRowIdSink sink = new SimpleRowIdSink();
//			sinks.add(sink);
//			RowIdProjectingSlaveTask task = new RowIdProjectingSlaveTask(getJob(), rowId, start, end, bitmaps, sink);
//			tasks.add(task);
//			
//			rowId += (Constants.ROWS_PER_PAGE * (end - start));
//			start = end;
//			end = Math.min(bitmaps.getCount(), start + getRuntime().configuration().getMinPageCountForRowIdProjectingSlaveTask());
//		}
//		
//		LatchCallback callback = new LatchCallback(tasks.size());
//		for(RowIdProjectingSlaveTask task : tasks) {
//			task.setCallback(callback);
//			getRuntime().executor().submit(task);
//		}
//		callback.waitDone();
//		return mergeRowIdSinks(sinks);
//	}
//	
//	private RowIdSource mergeRowIdSinks(List<SimpleRowIdSink> sinks) {
//		LinkedList<Long> rowIds = new LinkedList<Long>();
//		for(SimpleRowIdSink sink : sinks) {
//			rowIds.addAll(sink.getRowIds()); // copying performance ?
//		}
//		return new ListBackedRowIdSource(rowIds);
//	}
//}
