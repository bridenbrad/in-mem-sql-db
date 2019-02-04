/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter.array;

import java.util.Set;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.RowIdSink;

/**
 * This class was autogenerated. Do not edit manually.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class ByteArrayFilter extends AbstractArrayFilter {

	protected final byte[] array;
	
	public ByteArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array) {
		super(rowIdSink, startRowId);
		this.array = array;
	}
	
	public static final class ByteBetweenArrayFilter extends ByteArrayFilter {

		private final byte low;
		private final byte high;
		
		public ByteBetweenArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte low, byte high) {
			super(rowIdSink, startRowId, array);
			this.low = low;
			this.high = high;
		}

		@Override
		public void apply() {
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(low <= array[i] && array[i] <= high) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
	
	public static final class ByteInArrayFilter extends ByteArrayFilter {

		private final Set<Byte> set;

		public ByteInArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, Set<Byte> set) {
			super(rowIdSink, startRowId, array);
			this.set = set;
		}

		@Override
		public void apply() {	
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(set.contains(array[i])) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
	
	public static abstract class SimpleByteArrayFilter extends ByteArrayFilter {
		
		protected final byte value;

		public SimpleByteArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte value) {
			super(rowIdSink, startRowId, array);
			this.value = value;
		}
	}
	
	public static final class ByteEqArrayFilter extends SimpleByteArrayFilter {

		public ByteEqArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte value) {
			super(rowIdSink, startRowId, array, value);
		}

		@Override
		public void apply() {
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(array[i] == value) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
	
	public static final class ByteNeqArrayFilter extends SimpleByteArrayFilter {

		public ByteNeqArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte value) {
			super(rowIdSink, startRowId, array, value);
		}

		@Override
		public void apply() {
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(array[i] != value) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
	
	public static final class ByteLtArrayFilter extends SimpleByteArrayFilter {

		public ByteLtArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte value) {
			super(rowIdSink, startRowId, array, value);
		}

		@Override
		public void apply() {
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(array[i] < value) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
	
	public static final class ByteLtEqArrayFilter extends SimpleByteArrayFilter {

		public ByteLtEqArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte value) {
			super(rowIdSink, startRowId, array, value);
		}

		@Override
		public void apply() {
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(array[i] <= value) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
	
	public static final class ByteGtArrayFilter extends SimpleByteArrayFilter {

		public ByteGtArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte value) {
			super(rowIdSink, startRowId, array, value);
		}

		@Override
		public void apply() {			
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(array[i] > value) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
	
	public static final class ByteGtEqArrayFilter extends SimpleByteArrayFilter {

		public ByteGtEqArrayFilter(RowIdSink rowIdSink, long startRowId, byte[] array, byte value) {
			super(rowIdSink, startRowId, array, value);
		}

		@Override
		public void apply() {
			long rowId = getStartRowId();
			for (int i = 0; i < array.length; i++) {
				if(array[i] >= value) {
					getRowIdSink().onRowId(rowId);
				}
				++rowId;
			}
		}
	}
}