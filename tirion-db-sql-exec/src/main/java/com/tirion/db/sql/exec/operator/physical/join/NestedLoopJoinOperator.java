/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.join;

import java.util.LinkedList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.common.JoinType;
import com.tirion.db.sql.exec.operator.physical.filter.TuplesFilter;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NestedLoopJoinOperator extends JoinOperator {

	private TuplesFilter filter;
	
	private boolean consumedRight;
	private LinkedList<Tuple> rightTuples; // cache of all tuples from small right table
	private LinkedList<Tuple> builtTuples; // cache of built tuples
	
	private long consumedFromRight;
	private long consumedFromLeft;
	private long produced;
	
	public NestedLoopJoinOperator() {
		super();
		rightTuples = new LinkedList<Tuple>();
		builtTuples = new LinkedList<Tuple>();
	}

	public void setFilter(TuplesFilter filter) {
		this.filter = filter;
	}
	
	@Override
	public void shutdown() {
		rightTuples = null;
		builtTuples = null;
		super.shutdown();
	}

	@Override
	@JsonProperty
	public Statistics getStatistics() {
		return new NestedLoopOperatorStatistics(consumedFromRight, consumedFromLeft, produced);
	}

	@Override
	protected Tuple nextInternal() {		
		if(isDone()) {
			return null;
		}
		if(!consumedRight) {			
			consumeRight();
			consumedRight = true;
		}
		if (!builtTuples.isEmpty()) {
			++produced;
			return builtTuples.removeFirst();
		}
		while(true) {			
			Tuple tuple = nextFromLeft();
			if(tuple == null) {
				setDone();
				return null;
			}
			++consumedFromLeft;
			boolean matchedAtLeastOne = false;
			for(Tuple rightTuple : rightTuples) {	
				if(filter.matches(tuple, rightTuple)) {					
					builtTuples.add(buildJoinedTuple(tuple, rightTuple));
					matchedAtLeastOne = true;
				}
			}
			if(!matchedAtLeastOne && getJoinType() == JoinType.LEFT) { // case when LEFT join and no match 
				builtTuples.add(buildJoinedTuple(tuple, null));
			}
			if(builtTuples.isEmpty()) {
				continue;
			}
			++produced;
			return builtTuples.removeFirst();
		}
	}

	private void consumeRight() {
		while(true) {
			Tuple tuple = nextFromRight();
			if(tuple == null) {
				break;
			}
			rightTuples.add(tuple);
		}
		consumedFromRight = rightTuples.size();
	}
	
	private Tuple buildJoinedTuple(Tuple left, Tuple right) {
		return getTupleMerger().merge(left, right);
	}
}
