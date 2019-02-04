/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.window;
///**
// * 
// */
//package com.tirion.db.sql.query.operator.physical.window;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.tirion.common.Util;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
//import com.tirion.db.sql.query.operator.physical.groupby.key.ByteArrayKey;
//import com.tirion.db.sql.query.operator.physical.groupby.key.Key;
//import com.tirion.db.sql.query.operator.physical.groupby.key.KeyBuilder;
//import com.tirion.db.sql.query.operator.physical.window.framer.Framer;
//import com.tirion.db.sql.query.tuple.SimpleTuple;
//import com.tirion.db.sql.query.tuple.Tuple;
//import com.tirion.db.sql.query.tuple.comparator.TupleComparator;
//
///**
// * Data has to fit in memory.
// * 
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class InMemoryWindowOperator extends WindowOperator {
//
//	private LinkedList<Tuple> result; // tuples for parent operator
//	private Map<Key, Partition> map;
//	private boolean isSourceConsumed = false;
//	
//	private KeyBuilder keyBuilder;
//	private Functor functor;
//	private TupleComparator tupleComparator;
//	private Framer framer;
//	
//	public InMemoryWindowOperator() {
//		super();
//		map = new HashMap<Key, Partition>();
//		result = new LinkedList<Tuple>();
//	}
//	
//	public void setKeyBuilder(KeyBuilder keyBuilder) {
//		this.keyBuilder = keyBuilder;
//	}
//
//	public void setFunctor(Functor functor) {
//		this.functor = functor;
//	}
//
//	public void setTupleComparator(TupleComparator tupleComparator) {
//		this.tupleComparator = tupleComparator;
//	}
//
//	public void setFramer(Framer framer) {
//		this.framer = framer;
//	}
//	
//	@Override
//	protected Tuple nextInternal() {
//		if(!isSourceConsumed) {
//			buildPartitions();
//			sortPartitions();
//			buildColumnValues();
//			projectTuples();
//			isSourceConsumed = true;
//		}
//		if(result.isEmpty()) {
//			setDone(true);
//			return null;
//		}
//		return result.removeFirst();
//	}
//
//	private void buildPartitions() {
//		while(true) {
//			Tuple tuple = nextFromSource();
//			if(tuple == null) {
//				break;
//			}			
//			routeToPartition(tuple);
//		}
//	}
//	
//	private void routeToPartition(Tuple tuple) {
//		Key key = null;
//		if(keyBuilder != null) { // PARTITION BY clause
//			key = keyBuilder.build(tuple);
//		} else {  // no PARTITION BY clause, single partition for all
//			key = ByteArrayKey.EMPTY_KEY;
//		}
//		Partition partition = map.get(key);
//		if(partition == null) {
//			partition = new Partition();
//			map.put(key, partition);
//		}
//		partition.append(tuple);
//	}
//	
//	private void sortPartitions() {
//		for(Partition partition : map.values()) {
//			Collections.sort(partition.getTuples(), tupleComparator);
//		}
//	}
//	
//	private void buildColumnValues() {
//		for(Partition partition : map.values()) {
//			partition.initFunctors(functor);
//		}
//		for(Partition partition : map.values()) {		
//			framer.frame(partition.getTuples(), partition.getFunctors());
//		}
//	}
//	
//	private void projectTuples() {
//		for(Entry<Key, Partition> entry : map.entrySet()) {
//			Key key = entry.getKey();
//			Partition partition = entry.getValue();
//			processPartition(key, partition);
//		}
//		map = null;
//	}
//	
//	private void processPartition(Key key, Partition partition) {
//		for (int i = 0; i < partition.getCount(); i++) {			
//			Tuple tuple = partition.getTuples().get(i);
//			Functor functor = partition.getFunctors().get(i);
//			
//			byte[] data = new byte[getEntityInfo().getWidth()];
//			System.arraycopy(tuple.getData(), 0, data, 0, tuple.getData().length);
//			
//			Tuple outputTuple = new SimpleTuple(data);
//			functor.writeStateTo(outputTuple);
//			
//			result.add(outputTuple);
//		}
//	}
//	
//	private static final class Partition {
//		
//		private final List<Tuple> tuples;
//		private final List<Functor> functors;
//		
//		private Partition() {
//			super();
//			tuples = new ArrayList<Tuple>();
//			functors = new ArrayList<Functor>();
//		}
//
//		private int getCount() {
//			return tuples.size();
//		}
//		
//		private void append(Tuple tuple) {
//			tuples.add(tuple);
//		}
//		
//		private List<Tuple> getTuples() {
//			return tuples;
//		}
//		
//		// fill functors' list
//		private void initFunctors(Functor template) {
//			for (int i = 0; i < tuples.size(); i++) {
//				Functor functor = template.cloneMe();
//				functor.init();
//				functors.add(functor);
//			}
//			Util.assertTrue(tuples.size() == functors.size(), "Bug in code");
//		}
//
//		private List<Functor> getFunctors() {
//			return functors;
//		}
//	}
//}
