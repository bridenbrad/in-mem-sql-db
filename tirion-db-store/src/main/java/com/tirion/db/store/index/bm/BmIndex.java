/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.index.bm;

import java.util.Map;
import java.util.SortedSet;

import com.tirion.common.SizeAware;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.db.store.appender.Appendable;
import com.tirion.db.store.index.Index;
import com.tirion.executor.job.Job;

/**
 * Will throw exception at runtime if key cardinality is
 * violated.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface BmIndex<T> extends SizeAware, Index, Appendable<Map<T, Bitmaps>> {
	
	/**
	 * Maximum number of distinct keys per index.
	 */
	public static final int MAX_KEY_COUNT = 8192;

	/**
	 * Number of pages for each entry.
	 */
	int getPageCount();
	
	/**
	 * Number of distinct values being managed by
	 * this bitmap index.
	 */
	int getEntryCount();
	
	boolean hasValue(T value);
	
	/**
	 * Will add bitmaps for all missing pages.
	 */
	void registerValue(T value);
	
//	RowIdSource in(SortedSet<T> set, Job job);	
//	RowIdSource eq(T value, Job job);
//	RowIdSource neq(T value, Job job);
//	RowIdSource lt(T value, Job job);
//	RowIdSource ltEq(T value, Job job);
//	RowIdSource gt(T value, Job job);
//	RowIdSource gtEq(T value, Job job);
//	RowIdSource between(T low, T high, Job job);
	
	// used for fast multi-index evaluation
	Bitmaps getEq(T value, Job job);
	Bitmaps getNeq(T value, Job job);
	Bitmaps getLt(T value, Job job);
	Bitmaps getLtEq(T value, Job job);
	Bitmaps getGt(T value, Job job);
	Bitmaps getGtEq(T value, Job job);
	Bitmaps getBetween(T low, T high, Job job);
	Bitmaps getIn(SortedSet<T> set, Job job);
}
