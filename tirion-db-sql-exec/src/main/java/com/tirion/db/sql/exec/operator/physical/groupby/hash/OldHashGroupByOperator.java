/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.sql.exec.operator.physical.groupby.hash;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functors;
//import com.tirion.db.sql.exec.operator.physical.common.key.Key;
//import com.tirion.db.sql.exec.operator.physical.common.key.KeyBuilder;
//import com.tirion.db.sql.exec.operator.physical.groupby.GroupByOperator;
//import com.tirion.db.sql.exec.tuple.SimpleTuple;
//import com.tirion.db.sql.exec.tuple.Tuple;
//import com.tirion.profiler.statistics.Statistics;
//
///**
// * Suited for data sets that can fit into memory.
// * 
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class HashGroupByOperator extends GroupByOperator {
//	
//	protected Functors functors;
//	protected KeyBuilder keyBuilder;
//	private boolean isSourceConsumed = false;
//	private LinkedList<Tuple> outputTuples;	
//	
//	private long consumedFromSource;
//	private long keyCount;
//	private long mapBuildDuration;
//	private long tupleBuildDuration;
//
//	public void setKeyBuilder(KeyBuilder keyBuilder) {
//		this.keyBuilder = keyBuilder;
//	}
//
//	public void setFunctors(Functors functors) {
//		this.functors = functors;
//	}
//
//	@Override
//	public void init() {
//		super.init();
//		outputTuples = new LinkedList<Tuple>();
//	}
//
//	@Override
//	public void shutdown() {
//		outputTuples = null;
//		super.shutdown();
//	}
//	
//	@Override
//	public Statistics getStatistics() {
//		return new GroupingStatistics(consumedFromSource, keyCount, mapBuildDuration, tupleBuildDuration);
//	}
//
//	@Override
//	protected Tuple nextInternal() {
//		if(!isSourceConsumed) {
//			buildTuples();
//			isSourceConsumed = true;
//		}
//		if(outputTuples.isEmpty()) {
//			setDone();
//			return null;
//		}
//		return outputTuples.remove(0);
//	}
//
//	private void buildTuples() {
//		Map<Key, Functors> map = buildMap();
//		long start = System.currentTimeMillis();
//		for(Entry<Key, Functors> entry : map.entrySet()) {
//			Tuple outputTuple = new SimpleTuple(getOutputFieldCount());
//			entry.getKey().writeStateTo(outputTuple);
//			entry.getValue().writeStateTo(outputTuple);
//			outputTuples.add(outputTuple);
//		}
//		tupleBuildDuration = System.currentTimeMillis() - start;
//	}
//	
//	private Map<Key, Functors> buildMap() {
//		final long start = System.currentTimeMillis();
//		Map<Key, Functors> map = new HashMap<Key, Functors>();
//		while(true) {
//			Tuple tuple = nextFromSource();
//			if(tuple == null) {
//				break;
//			}
//			++consumedFromSource;
//			Key key = keyBuilder.build(tuple);
//			Functors functors = map.get(key);
//			if(functors == null) {
//				functors = newFunctors();
//				map.put(key, functors);
//			}
//			functors.onTuple(tuple);
//		}
//		mapBuildDuration = System.currentTimeMillis() - start;
//		keyCount = map.size();
//		return map;
//	}
//	
//	private Functors newFunctors() {
//		Functors result= functors.cloneMe();
//		result.init();
//		return result;
//	}
//	
//	private int getOutputFieldCount() {
//		return functors.getCount() + keyBuilder.getPartCount();
//	}
//}
