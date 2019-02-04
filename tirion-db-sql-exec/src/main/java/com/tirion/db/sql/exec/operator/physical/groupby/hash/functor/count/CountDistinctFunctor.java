/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.count;

import java.util.HashSet;
import java.util.Set;

import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.AbstractFunctor;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Cardinality limited by Java's int size. FIXME
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class CountDistinctFunctor extends AbstractFunctor {
	
	public CountDistinctFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public final int getWidth() {
		return Type.INT.getWidth();
	}

	@Override
	public final void writeStateTo(Tuple tuple) {
		tuple.putInteger(getOutputIndex(), getDistinctCount());
	}
	
	protected abstract int getDistinctCount();
	
	public static final class ByteCountDistinctFunctor extends CountDistinctFunctor {

		private Set<Byte> set;
		
		public ByteCountDistinctFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public void init() {
			set = new HashSet<Byte>();
		}

		@Override
		public void shutdown() {
			set = null;
		}

		@Override
		protected void onTupleInternal(Tuple tuple) {
			set.add(tuple.getByte(getInputIndex()));
		}

		@Override
		protected int getDistinctCount() {
			return set.size();
		}
		
		@Override
		public Functor cloneMe() {
			return new ByteCountDistinctFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class ShortCountDistinctFunctor extends CountDistinctFunctor {

		private Set<Short> set;
		
		public ShortCountDistinctFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public void init() {
			set = new HashSet<Short>();
		}

		@Override
		public void shutdown() {
			set = null;
		}

		@Override
		protected void onTupleInternal(Tuple tuple) {
			set.add(tuple.getShort(getInputIndex()));
		}

		@Override
		protected int getDistinctCount() {
			return set.size();
		}
		
		@Override
		public Functor cloneMe() {
			return new ShortCountDistinctFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class IntegerCountDistinctFunctor extends CountDistinctFunctor {

		private Set<Integer> set;
		
		public IntegerCountDistinctFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public void init() {
			set = new HashSet<Integer>();
		}

		@Override
		public void shutdown() {
			set = null;
		}

		@Override
		protected void onTupleInternal(Tuple tuple) {
			set.add(tuple.getInteger(getInputIndex()));
		}

		@Override
		protected int getDistinctCount() {
			return set.size();
		}
		
		@Override
		public Functor cloneMe() {
			return new IntegerCountDistinctFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class LongCountDistinctFunctor extends CountDistinctFunctor {

		private Set<Long> set;
		
		public LongCountDistinctFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public void init() {
			set = new HashSet<Long>();
		}

		@Override
		public void shutdown() {
			set = null;
		}

		@Override
		protected void onTupleInternal(Tuple tuple) {
			set.add(tuple.getLong(getInputIndex()));
		}

		@Override
		protected int getDistinctCount() {
			return set.size();
		}
		
		@Override
		public Functor cloneMe() {
			return new LongCountDistinctFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class FloatCountDistinctFunctor extends CountDistinctFunctor {

		private Set<Float> set;
		
		public FloatCountDistinctFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public void init() {
			set = new HashSet<Float>();
		}

		@Override
		public void shutdown() {
			set = null;
		}

		@Override
		protected void onTupleInternal(Tuple tuple) {
			set.add(tuple.getFloat(getInputIndex()));
		}

		@Override
		protected int getDistinctCount() {
			return set.size();
		}
		
		@Override
		public Functor cloneMe() {
			return new FloatCountDistinctFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class DoubleCountDistinctFunctor extends CountDistinctFunctor {

		private Set<Double> set;
		
		public DoubleCountDistinctFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public void init() {
			set = new HashSet<Double>();
		}

		@Override
		public void shutdown() {
			set = null;
		}

		@Override
		protected void onTupleInternal(Tuple tuple) {
			set.add(tuple.getDouble(getInputIndex()));
		}

		@Override
		protected int getDistinctCount() {
			return set.size();
		}

		@Override
		public Functor cloneMe() {
			return new DoubleCountDistinctFunctor(getInputIndex(), getOutputIndex());
		}
	}
}
