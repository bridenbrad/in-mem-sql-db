/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter.buffer;

import java.nio.ByteBuffer;
import java.util.Set;

import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.RowIdSink;

/**
 * This class was autogenerated. Do not edit manually.
 * 
 * @author Veljko Zivkovic
 */
public abstract class FloatBufferFilter extends AbstractBufferFilter {

	public FloatBufferFilter(RowIdSink rowIdSink, long startRowId, int startPosition, int count, ByteBuffer buffer) {
		super(rowIdSink, startRowId, startPosition, count, buffer);
	}

	public static final class FloatBetweenBufferFilter extends FloatBufferFilter {

		private final float low;
		private final float high;
		
		public FloatBetweenBufferFilter(RowIdSink rowIdSink, long startRowId, int startPosition, int count, ByteBuffer buffer, float low, float high) {
			super(rowIdSink, startRowId, startPosition, count, buffer);
			this.low = low;
			this.high = high;
		}

		@Override
		public void apply() {	
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							final float value = buffer.getFloat(position);
							if(low <= value && value <= high) {
					rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class FloatInBufferFilter extends FloatBufferFilter {

		private final Set<Float> set;

		public FloatInBufferFilter(RowIdSink rowIdSink, long startRowId, int startPosition, int count, ByteBuffer buffer, Set<Float> set) {
			super(rowIdSink, startRowId, startPosition, count, buffer);
			this.set = set;
		}

		@Override
		public void apply() {	
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							final float value = buffer.getFloat(position);
							if(set.contains(value)) {
					rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
	
	public static abstract class SimpleFloatBufferFilter extends FloatBufferFilter {
		
		protected final float value;

		public SimpleFloatBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, float value) {
			super(rowIdSink, startRowId, position, count, buffer);
			this.value = value;
		}
	}
	
	public static final class FloatEqBufferFilter extends SimpleFloatBufferFilter {

		public FloatEqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, float value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			long rowId = startRowId;
			int position = startPosition;
			for (int i = 0; i < count; i++) {
							if(buffer.getFloat(position) == value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class FloatNeqBufferFilter extends SimpleFloatBufferFilter {

		public FloatNeqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, float value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getFloat(position) != value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class FloatLtBufferFilter extends SimpleFloatBufferFilter {

		public FloatLtBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, float value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getFloat(position) < value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class FloatLtEqBufferFilter extends SimpleFloatBufferFilter {

		public FloatLtEqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, float value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getFloat(position) <= value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class FloatGtBufferFilter extends SimpleFloatBufferFilter {

		public FloatGtBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, float value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getFloat(position) > value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class FloatGtEqBufferFilter extends SimpleFloatBufferFilter {

		public FloatGtEqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, float value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getFloat(position) >= value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.FLOAT.getWidth();
				++rowId;
			}
		}
	}
}