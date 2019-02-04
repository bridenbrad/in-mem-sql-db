/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.join;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.common.JoinType;
import com.tirion.db.sql.exec.operator.physical.common.key.Key;
import com.tirion.db.sql.exec.operator.physical.common.key.KeyBuilder;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class HashJoinOperator extends JoinOperator {

	@JsonProperty
	private KeyBuilder leftKeyBuilder;
	@JsonProperty
	private KeyBuilder rightKeyBuilder;

	private boolean consumedRight;
	private Map<Key, List<Tuple>> rightMap;
	private LinkedList<Tuple> builtTuples; // cache of built tuples

	private long consumedFromRight;
	private long keyCount;
	private long mapBuildDuration;
	private long consumedFromLeft;
	private long produced;
	
	public HashJoinOperator() {
		super();
		builtTuples = new LinkedList<Tuple>();
		rightMap = new HashMap<Key, List<Tuple>>();
	}

	public void setLeftKeyBuilder(KeyBuilder leftKeyBuilder) {
		this.leftKeyBuilder = leftKeyBuilder;
	}

	public void setRightKeyBuilder(KeyBuilder rightKeyBuilder) {
		this.rightKeyBuilder = rightKeyBuilder;
	}
	
	@Override
	public void shutdown() {
		rightMap = null;
		builtTuples = null;
		super.shutdown();
	}

	@Override
	@JsonProperty
	public Statistics getStatistics() {
		return new HashJoinOperatorStatistics(consumedFromRight, keyCount, mapBuildDuration, consumedFromLeft, produced);
	}

	@Override
	protected Tuple nextInternal() {
		if (isDone()) {
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
		while (true) {
			Tuple tuple = nextFromLeft();
			if (tuple == null) {
				setDone();
				return null;
			}
			++consumedFromLeft;
			List<Tuple> tuples = rightMap.get(leftKeyBuilder.build(tuple));
			if (tuples == null || tuples.isEmpty()) {
				if (getJoinType() == JoinType.LEFT) {
					builtTuples.add(buildJoinedTuple(tuple, null));
				}
			} else {
				for (Tuple rightTuple : tuples) {
					builtTuples.add(buildJoinedTuple(tuple, rightTuple));
				}
			}
			if(!builtTuples.isEmpty()) {
				++produced;				
				return builtTuples.removeFirst();
			}
		}
	}

	private void consumeRight() {
		long startTime = System.currentTimeMillis();
		while (true) {
			Tuple tuple = nextFromRight();
			if (tuple == null) {
				break;
			}
			++consumedFromRight;
			Key key = rightKeyBuilder.build(tuple);
			List<Tuple> tuples = rightMap.get(key);
			if (tuples == null) {
				tuples = new LinkedList<Tuple>();
				rightMap.put(key, tuples);
			}
			tuples.add(tuple);
		}
		keyCount = rightMap.size();
		mapBuildDuration = System.currentTimeMillis() - startTime;
	}

	private Tuple buildJoinedTuple(Tuple left, Tuple right) {		
		return getTupleMerger().merge(left, right);
	}
}