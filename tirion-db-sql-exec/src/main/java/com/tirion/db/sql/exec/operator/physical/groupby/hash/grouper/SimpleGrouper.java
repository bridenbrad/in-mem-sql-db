/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.tirion.db.sql.exec.operator.physical.common.key.Key;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.GroupingStatistics;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functors;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.FunctorsFactory;
import com.tirion.db.sql.exec.tuple.OnHeapTuple;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleGrouper implements Grouper {

	private final int outputFieldCount;
	private final FunctorsFactory factory;
	private Map<Key, Functors> map = new HashMap<Key, Functors>();
	
	private long consumedTuples;
	private long keyCount;
	private long flushDuration = -1;
	
	public SimpleGrouper(int outputFieldCount, FunctorsFactory factory) {
		super();
		this.outputFieldCount = outputFieldCount;
		this.factory = factory;
	}

	@Override
	public Statistics getStatistics() {
		return new GroupingStatistics(consumedTuples, keyCount, flushDuration);
	}

	@Override
	public void onTuple(Tuple tuple, Key key) {
		Functors functors = map.get(key);
		if(functors == null) {
			functors = factory.newFunctors();
			map.put(key, functors);
			++keyCount;
		}
		functors.onTuple(tuple);	
		++consumedTuples;
	}

	@Override
	public LinkedList<Tuple> getResult() {
		final long start = System.currentTimeMillis();
		LinkedList<Tuple> tuples = new LinkedList<Tuple>();
		for(java.util.Map.Entry<Key, Functors> entry : map.entrySet()) {
			Tuple tuple = new OnHeapTuple(outputFieldCount);
			entry.getKey().writeStateTo(tuple);
			entry.getValue().writeStateTo(tuple);
			tuples.add(tuple);
		}
		map.clear();
		map = null;
		flushDuration = System.currentTimeMillis() - start;
		return tuples;
	}
}
