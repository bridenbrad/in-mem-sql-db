/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper;

import java.util.LinkedList;

import com.tirion.db.sql.exec.operator.physical.common.key.Key;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.FunctorsFactory;
import com.tirion.db.sql.exec.tuple.EmptyTuple;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.executor.job.Job;
import com.tirion.stats.MapStatistics;
import com.tirion.stats.Statistics;
import com.tirion.stats.Statistics.Kind;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ParallelGrouper implements Grouper {

	private final Grouper[] underlyings;
	
	public ParallelGrouper(int grouperCount, int outputFieldCount, FunctorsFactory factory, Job job) {
		super();
		underlyings = new Grouper[grouperCount];
		for (int i = 0; i < underlyings.length; i++) {
			Grouper grouper = new SimpleGrouper(outputFieldCount, factory);
			Grouper proxy = new ProxyGrouper(grouper, job);
			underlyings[i] = proxy;
		}
	}
	
	@Override
	public Statistics getStatistics() {
		MapStatistics stats = new MapStatistics(Kind.GROUPER_STATISTICS);
		for (int i = 0; i < underlyings.length; i++) {
			stats.add(String.valueOf(i), underlyings[i].getStatistics());
		}
		return stats;
	}

	@Override
	public void onTuple(Tuple tuple, Key key) {
		if(tuple == null) {
			for(Grouper grouper : underlyings) {
				grouper.onTuple(EmptyTuple.EOS_TUPLE, null);
			}
		} else {
			underlyings[key.hashCode() % underlyings.length].onTuple(tuple, key);		
		}
	}
	
	// TODO performance, this copying could be slow
	@Override
	public LinkedList<Tuple> getResult() {
		LinkedList<Tuple> result = new LinkedList<Tuple>();
		for (int i = 0; i < underlyings.length; i++) {
			result.addAll(underlyings[i].getResult());
		}
		return result;
	}
}
