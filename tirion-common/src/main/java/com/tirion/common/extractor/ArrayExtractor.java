/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.extractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class ArrayExtractor implements Extractor {

	public abstract Object extract(int index, Object array);
	public abstract Object extract(List<Integer> indexes, Object array);
	
	public static final class ByteArrayExtractor extends ArrayExtractor {

		@Override
		public Object extract(int index, Object array) {
			return ((byte[])array)[index];
		}

		@Override
		public Object extract(List<Integer> indexes, Object array) {
			List<Object> result = new ArrayList<Object>(indexes.size());
			byte[] arr = (byte[]) array;
			for(Integer index : indexes) {
				result.add(arr[index]);
			}
			return result;
		}
	}
	
	public static final class ShortArrayExtractor extends ArrayExtractor {

		@Override
		public Object extract(int index, Object array) {
			return ((short[])array)[index];
		}
		
		@Override
		public Object extract(List<Integer> indexes, Object array) {
			List<Object> result = new ArrayList<Object>(indexes.size());
			short[] arr = (short[]) array;
			for(Integer index : indexes) {
				result.add(arr[index]);
			}
			return result;
		}
	}
	
	public static final class IntegerArrayExtractor extends ArrayExtractor {

		@Override
		public Object extract(int index, Object array) {
			return ((int[])array)[index];
		}
		
		@Override
		public Object extract(List<Integer> indexes, Object array) {
			List<Object> result = new ArrayList<Object>(indexes.size());
			int[] arr = (int[]) array;
			for(Integer index : indexes) {
				result.add(arr[index]);
			}
			return result;
		}
	}
	
	public static final class LongArrayExtractor extends ArrayExtractor {

		@Override
		public Object extract(int index, Object array) {
			return ((long[])array)[index];
		}
		
		@Override
		public Object extract(List<Integer> indexes, Object array) {
			List<Object> result = new ArrayList<Object>(indexes.size());
			long[] arr = (long[]) array;
			for(Integer index : indexes) {
				result.add(arr[index]);
			}
			return result;
		}
	}
	
	public static final class FloatArrayExtractor extends ArrayExtractor {

		@Override
		public Object extract(int index, Object array) {
			return ((float[])array)[index];
		}
		
		@Override
		public Object extract(List<Integer> indexes, Object array) {
			List<Object> result = new ArrayList<Object>(indexes.size());
			float[] arr = (float[]) array;
			for(Integer index : indexes) {
				result.add(arr[index]);
			}
			return result;
		}
	}
	
	public static final class DoubleArrayExtractor extends ArrayExtractor {

		@Override
		public Object extract(int index, Object array) {
			return ((double[])array)[index];
		}
		
		@Override
		public Object extract(List<Integer> indexes, Object array) {
			List<Object> result = new ArrayList<Object>(indexes.size());
			double[] arr = (double[]) array;
			for(Integer index : indexes) {
				result.add(arr[index]);
			}
			return result;
		}
	}
}
