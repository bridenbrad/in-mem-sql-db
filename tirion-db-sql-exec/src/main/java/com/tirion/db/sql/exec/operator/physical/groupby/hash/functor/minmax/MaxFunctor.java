/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.minmax;

import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.AbstractFunctor;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class MaxFunctor extends AbstractFunctor {

	public MaxFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}
	
	public static final class ByteMaxFunctor extends MaxFunctor {

		private byte max = Byte.MIN_VALUE;
		
		public ByteMaxFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.BYTE.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			max = (byte) Math.max(max, tuple.getByte(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putByte(getOutputIndex(), max);
		}
		
		@Override
		public Functor cloneMe() {
			return new ByteMaxFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class ShortMaxFunctor extends MaxFunctor {

		private short max = Short.MIN_VALUE;
		
		public ShortMaxFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.SHORT.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			max = (short) Math.max(max, tuple.getShort(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putShort(getOutputIndex(), max);
		}
		
		@Override
		public Functor cloneMe() {
			return new ShortMaxFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class IntegerMaxFunctor extends MaxFunctor {

		private int max = Integer.MIN_VALUE;
		
		public IntegerMaxFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.INT.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			max = Math.max(max, tuple.getInteger(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putInteger(getOutputIndex(), max);
		}
		
		@Override
		public Functor cloneMe() {
			return new IntegerMaxFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class LongMaxFunctor extends MaxFunctor {

		private long max = Long.MIN_VALUE;
		
		public LongMaxFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.LONG.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			max = Math.max(max, tuple.getLong(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putLong(getOutputIndex(), max);
		}
		
		@Override
		public Functor cloneMe() {
			return new LongMaxFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class FloatMaxFunctor extends MaxFunctor {

		private float max = Float.MIN_VALUE;
		
		public FloatMaxFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.FLOAT.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			max = Math.max(max, tuple.getFloat(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putFloat(getOutputIndex(), max);
		}

		@Override
		public Functor cloneMe() {
			return new FloatMaxFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class DoubleMaxFunctor extends MaxFunctor {

		private double max = Double.MIN_VALUE;
		
		public DoubleMaxFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.DOUBLE.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			max = Math.max(max, tuple.getDouble(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putDouble(getOutputIndex(), max);
		}

		@Override
		public Functor cloneMe() {
			return new DoubleMaxFunctor(getInputIndex(), getOutputIndex());
		}
	}
}
