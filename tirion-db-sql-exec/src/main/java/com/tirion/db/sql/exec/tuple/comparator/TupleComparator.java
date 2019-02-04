/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TupleComparator implements Comparator<Tuple> {
	
	public static Orderer newOrderer(Type type, int index, boolean ascending) {
		switch (type) {
			case BOOLEAN:
			case BYTE:
				return new ByteOrderer(index, ascending);
			case DATE:
			case SHORT:
				return new ShortOrderer(index, ascending);
			case TIME:
			case INT:
				return new IntOrderer(index, ascending);
			case TIMESTAMP:
			case LONG:
				return new LongOrderer(index, ascending);
			case FLOAT:
				return new FloatOrderer(index, ascending);
			case DOUBLE:
				return new DoubleOrderer(index, ascending);
			case VARCHAR:
				return new StringOrderer(index, ascending);
			default:
				throw new NotYetImplementedException(type.toString());
		}
	}

	@JsonProperty
	private final List<Orderer> orderers;
	
	public TupleComparator() {
		super();
		orderers = new ArrayList<Orderer>();
	}

	public void append(Orderer orderer) {
		orderers.add(orderer);
	}

	@Override
	public int compare(Tuple left, Tuple right) {
		for(Orderer orderer : orderers) {
			int result = orderer.order(left, right);
			if(result < 0) {
				return -1;
			} else if(result > 0) {
				return 1;
			}
		}
		// tuples are logically the same, decide based on default Object's hash code;
		return Integer.compare(left.hashCode(), right.hashCode());
	}
	
	/**
	 * Total order for tuples.
	 */
	public static interface Orderer {
		
		/**
		 * Returns 0 if x == y; a value less than 0 if x < y; and a value greater 
		 * than 0 if x > y.
		 */
		int order(Tuple left, Tuple right);
	}
	
	public static abstract class AbstractOrderer implements Orderer {
		
		@JsonProperty
		private final int index;
		@JsonProperty
		private final boolean ascending;

		public AbstractOrderer(int index, boolean ascending) {
			this.index = index;
			this.ascending = ascending;
		}
		
		protected final int getIndex() {
			return index;
		}
		
		protected final boolean isAscending() {
			return ascending;
		}

		@Override
		public int order(Tuple left, Tuple right) {
			final boolean leftNull = left.isNull(getIndex());
			final boolean rightNull = right.isNull(getIndex());
			if(leftNull && rightNull) {
				return 0;
			} else if(leftNull) {
				return -1;
			} else if(rightNull) {
				return 1;
			} else {
				return orderInternal(left, right);
			}
		}
		
		protected abstract int orderInternal(Tuple left, Tuple right);
	}
	
	public static final class BooleanOrderer extends AbstractOrderer {

		public BooleanOrderer(int index, boolean ascending) {
			super(index, ascending);
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			boolean leftValue = left.getBoolean(getIndex());
			boolean rightValue = right.getBoolean(getIndex());
			int result = Boolean.compare(leftValue, rightValue);
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}

	public static final class ByteOrderer extends AbstractOrderer {

		public ByteOrderer(int index, boolean ascending) {
			super(index, ascending);
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			byte leftValue = left.getByte(getIndex());
			byte rightValue = right.getByte(getIndex());
			int result = Byte.compare(leftValue, rightValue);
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}
	
	public static final class ShortOrderer extends AbstractOrderer {

		public ShortOrderer(int index, boolean ascending) {
			super(index, ascending);
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			short leftValue = left.getShort(getIndex());
			short rightValue = right.getShort(getIndex());
			int result = Short.compare(leftValue, rightValue);
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}
	
	public static final class IntOrderer extends AbstractOrderer {

		public IntOrderer(int index, boolean ascending) {
			super(index, ascending);
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			int leftValue = left.getInteger(getIndex());
			int rightValue = right.getInteger(getIndex());
			int result = Integer.compare(leftValue, rightValue);
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}
	
	public static final class LongOrderer extends AbstractOrderer {

		public LongOrderer(int index, boolean ascending) {
			super(index, ascending);
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			long leftValue = left.getLong(getIndex());
			long rightValue = right.getLong(getIndex());
			int result = Long.compare(leftValue, rightValue);
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}
	
	public static final class FloatOrderer extends AbstractOrderer {

		public FloatOrderer(int index, boolean ascending) {
			super(index, ascending);
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			float leftValue = left.getFloat(getIndex());
			float rightValue = right.getFloat(getIndex());
			int result = Float.compare(leftValue, rightValue);
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}
	
	public static final class DoubleOrderer extends AbstractOrderer {

		public DoubleOrderer(int index, boolean ascending) {
			super(index, ascending);
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			double leftValue = left.getDouble(getIndex());
			double rightValue = right.getDouble(getIndex());
			int result = Double.compare(leftValue, rightValue);
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}
	
	public static final class StringOrderer extends AbstractOrderer {

		@JsonProperty
		private final boolean caseSensitive;
				
		/**
		 * Default is case sensitive.
		 */
		public StringOrderer(int index, boolean ascending) {
			this(index, ascending, true);
		}
		
		public StringOrderer(int index, boolean ascending, boolean caseSensitive) {
			super(index, ascending);
			this.caseSensitive = caseSensitive;
		}

		@Override
		protected int orderInternal(Tuple left, Tuple right) {
			String leftValue = left.getString(getIndex());
			String rightValue = right.getString(getIndex());
			
			int result = 0;
			if(caseSensitive) {
				result = leftValue.compareTo(rightValue);
			} else {				
				result = leftValue.compareToIgnoreCase(rightValue);
			}
			if(!isAscending()) {
				result = (-result);
			}
			return result;
		}
	}
}
