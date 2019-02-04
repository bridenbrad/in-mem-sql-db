/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.exec;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.merger.RowIdMerger;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.merger.SimpleRowIdMerger;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
import com.tirion.db.store.index.bm.task2.BitmapsMasterTask;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class CompositeOperation extends AbstractOperation {
	
	@JsonProperty
	protected final List<IndexedOperation> indexed = new ArrayList<IndexedOperation>();
//	@JsonProperty
//	protected final List<NonIndexedOperation> nonIndexed = new ArrayList<NonIndexedOperation>();
	@JsonProperty
	protected final List<CompositeOperation> composite = new ArrayList<CompositeOperation>();
	
	protected final RowIdMerger merger = new SimpleRowIdMerger();
	
	public CompositeOperation(Job job) {
		super(job);
	}

	protected final boolean hasComposite() {
		return !composite.isEmpty();
	}
	
	protected final boolean hasNonIndexed() {
//		return !nonIndexed.isEmpty();
		throw new NotYetImplementedException();
	}
	
	protected final boolean hasIndexed() {
		return !indexed.isEmpty();
	}

	public final void append(Object operation) {
//		if(operation instanceof IndexedOperation) {
//			indexed.add((IndexedOperation)operation);
//		} else if(operation instanceof NonIndexedOperation) {
//			nonIndexed.add((NonIndexedOperation)operation);
//		} else if(operation instanceof CompositeOperation) {
//			composite.add((CompositeOperation)operation);
//		} else {
//			throw new IllegalArgumentException("Unexpected class " + operation.getClass().getName());
//		}
	}
	
	public abstract RowIdSource execute();
	
	/**
	 * If parameter is true and at least one operation returns empty result, null will
	 * be returned. If false, all nulls returned will be skipped.
	 */
	protected final Bitmaps executeIndexedOperations(boolean isAnd) {
		try {
			LinkedList<Bitmaps> lists = new LinkedList<Bitmaps>();
			for(IndexedOperation operation : indexed) {
				Bitmaps bitmaps = operation.execute(); // indexed ops are never filtered
				if(bitmaps == null || bitmaps.isEmpty()) {
					if(isAnd) {
						return null;
					} else {
						continue;
					}
				}
				lists.add(bitmaps);
			}
			if(lists.isEmpty()) {
				return null;
			}
			BitmapsMasterTask task = null;
			int count = lists.get(0).getCount();
			if(isAnd) {			
//				task = new BitmapsMasterTask.AndEwahBitmapsMasterTask(getJob(), count, lists);
			} else {
//				task = new BitmapsMasterTask.OrEwahBitmapsMasterTask(getJob(), count, lists);
			}
			return task.call();
		} catch (Exception e) {
			throw new RuntimeException("Exception while executing indexed operations of composite operation", e);
		}
	}
}
