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
public abstract class MinFunctor extends AbstractFunctor {

	public MinFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}
	
	public static final class ByteMinFunctor extends MinFunctor {

		private byte min = Byte.MAX_VALUE;
	
		public ByteMinFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.BYTE.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			min = (byte) Math.min(min, tuple.getByte(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putByte(getOutputIndex(), min);
		}
		
		@Override
		public Functor cloneMe() {
			return new ByteMinFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class ShortMinFunctor extends MinFunctor {

		private short min = Short.MAX_VALUE;
		
		public ShortMinFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.SHORT.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			min = (short) Math.min(min, tuple.getShort(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putShort(getOutputIndex(), min);
		}

		@Override
		public Functor cloneMe() {
			return new ShortMinFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class IntegerMinFunctor extends MinFunctor {

		private int min = Integer.MAX_VALUE;
		
		public IntegerMinFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.INT.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			min = Math.min(min, tuple.getInteger(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putInteger(getOutputIndex(), min);
		}

		@Override
		public Functor cloneMe() {
			return new IntegerMinFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class LongMinFunctor extends MinFunctor {

		private long min = Long.MAX_VALUE;
		
		public LongMinFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.LONG.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			min = Math.min(min, tuple.getLong(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putLong(getOutputIndex(), min);
		}

		@Override
		public Functor cloneMe() {
			return new LongMinFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class FloatMinFunctor extends MinFunctor {

		private float min = Float.MAX_VALUE;
		
		public FloatMinFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.FLOAT.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			min = Math.min(min, tuple.getFloat(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putFloat(getOutputIndex(), min);
		}

		@Override
		public Functor cloneMe() {
			return new FloatMinFunctor(getInputIndex(), getOutputIndex());
		}
	}
	
	public static final class DoubleMinFunctor extends MinFunctor {

		private double min = Double.MAX_VALUE;
		
		public DoubleMinFunctor(int inputIndex, int outputIndex) {
			super(inputIndex, outputIndex);
		}

		@Override
		public int getWidth() {
			return Type.DOUBLE.getWidth();
		}
		
		@Override
		protected void onTupleInternal(Tuple tuple) {
			min = Math.min(min, tuple.getDouble(getInputIndex()));
		}
		
		@Override
		public void writeStateTo(Tuple tuple) {
			tuple.putDouble(getOutputIndex(), min);
		}

		@Override
		public Functor cloneMe() {
			return new DoubleMinFunctor(getInputIndex(), getOutputIndex());
		}
	}
}
