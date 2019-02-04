/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.window.framer;
//package com.tirion.db.sql.query.operator.physical.window.framer;
//
//import java.util.List;
//
//import com.tirion.common.NotYetImplementedException;
//import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
//import com.tirion.db.sql.query.tuple.Tuple;
//import com.tirion.db.sql.query.tuple.Tuples;
//import com.tirion.db.sql.query.tuple.source.TupleSource;
//
///**
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public final class RowFramer extends AbstractFramer {
//
//	private final int precedingCount;
//	private final int followingCount;
//	
//	public RowFramer(int precedingCount, int followingCount) {
//		super();
//		this.precedingCount = precedingCount;
//		this.followingCount = followingCount;
//	}
//
//	@Override
//	public void frame(List<Tuple> tuples, List<Functor> functors) {
//		for (int i = 0; i < tuples.size(); i++) {
//			
//			int precedingIndex = i;
//			if(precedingCount > 0) {				
//				precedingIndex = Math.max(0, precedingIndex - precedingCount);
//			}
//			int followingIndex = i;
//			if(followingCount > 0) {				
//				followingIndex = Math.min(tuples.size() - 1, followingIndex + followingCount);
//			}
//			
//			TupleSource tupleSource = new FrameTupleSource(precedingIndex, followingIndex, tuples);
//			
//			Functor functor = functors.get(i);
//			while(true) {
//				Tuple tuple = tupleSource.next();
//				if(tuple == null) {
//					break;
//				}
//				functor.onTuple(tuple);
//			}
//		}
//	}
//	
//	private static final class FrameTupleSource implements TupleSource {
//
//		private final int endIndex;
//		private final List<Tuple> tuples;
//		
//		private int currentIndex;
//		
//		private FrameTupleSource(int startIndex, int endIndex, List<Tuple> tuples) {
//			super();
//			this.endIndex = endIndex;
//			this.tuples = tuples;
//			currentIndex = startIndex;
//		}
//
//		@Override
//		public Tuple next() {
//			if(currentIndex > endIndex) {
//				return null;
//			}
//			return tuples.get(currentIndex++);
//		}		
//		@Override
//		public boolean supportsBatch() {
//			throw new NotYetImplementedException();
//		}
//
//		@Override
//		public Tuples nextBatch() {
//			throw new NotYetImplementedException();
//		}
//	}
//}
