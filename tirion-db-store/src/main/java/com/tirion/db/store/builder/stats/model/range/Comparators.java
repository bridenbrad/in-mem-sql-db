/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.model.range;

import java.util.Comparator;

/**
 * Compares ranges based on benefit: the wider the range
 * the higher the benefit. Sorts in increasing benefit.
 */
public abstract class Comparators {

	public static final class ByteRangeComparator implements Comparator<ByteRange> {
		@Override
		public int compare(ByteRange left, ByteRange right) {
			byte leftDiff = left.getDiff();
			byte rightDiff = right.getDiff();
			if(leftDiff < rightDiff) {
				return -1;
			} else if(leftDiff > rightDiff) {
				return 1;
			} else {				
				return 0;
			}
		}
	}
	
	public static final class ShortRangeComparator implements Comparator<ShortRange> {
		@Override
		public int compare(ShortRange left, ShortRange right) {
			short leftDiff = left.getDiff();
			short rightDiff = right.getDiff();
			if(leftDiff < rightDiff) {
				return -1;
			} else if(leftDiff > rightDiff) {
				return 1;
			} else {				
				return 0;
			}
		}
	}
	
	public static final class IntegerRangeComparator implements Comparator<IntegerRange> {
		@Override
		public int compare(IntegerRange left, IntegerRange right) {
			int leftDiff = left.getDiff();
			int rightDiff = right.getDiff();
			if(leftDiff < rightDiff) {
				return -1;
			} else if(leftDiff > rightDiff) {
				return 1;
			} else {				
				return 0;
			}
		}
	}
	
	public static final class LongRangeComparator implements Comparator<LongRange> {
		@Override
		public int compare(LongRange left, LongRange right) {
			long leftDiff = left.getDiff();
			long rightDiff = right.getDiff();
			if(leftDiff < rightDiff) {
				return -1;
			} else if(leftDiff > rightDiff) {
				return 1;
			} else {				
				return 0;
			}
		}
	}
	
	/**
	 * TODO tuning, perhaps assume 2 digits and 1.0-2.0 range would actually
	 * have difference of 100 values etc
	 */
	public static final class FloatRangeComparator implements Comparator<FloatRange> {
		@Override
		public int compare(FloatRange left, FloatRange right) {
			float leftDiff = left.getDiff();
			float rightDiff = right.getDiff();
			if(leftDiff < rightDiff) {
				return -1;
			} else if(leftDiff > rightDiff) {
				return 1;
			} else {				
				return 0;
			}
		}
	}
	
	public static final class DoubleRangeComparator implements Comparator<DoubleRange> {
		@Override
		public int compare(DoubleRange left, DoubleRange right) {
			double leftDiff = left.getDiff();
			double rightDiff = right.getDiff();
			if(leftDiff < rightDiff) {
				return -1;
			} else if(leftDiff > rightDiff) {
				return 1;
			} else {				
				return 0;
			}
		}
	}
	
	private Comparators(){ 
	}
}
