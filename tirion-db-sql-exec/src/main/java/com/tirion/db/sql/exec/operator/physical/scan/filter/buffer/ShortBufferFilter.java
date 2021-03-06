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
public abstract class ShortBufferFilter extends AbstractBufferFilter {

	public ShortBufferFilter(RowIdSink rowIdSink, long startRowId, int startPosition, int count, ByteBuffer buffer) {
		super(rowIdSink, startRowId, startPosition, count, buffer);
	}

	public static final class ShortBetweenBufferFilter extends ShortBufferFilter {

		private final short low;
		private final short high;
		
		public ShortBetweenBufferFilter(RowIdSink rowIdSink, long startRowId, int startPosition, int count, ByteBuffer buffer, short low, short high) {
			super(rowIdSink, startRowId, startPosition, count, buffer);
			this.low = low;
			this.high = high;
		}

		@Override
		public void apply() {	
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							final short value = buffer.getShort(position);
							if(low <= value && value <= high) {
					rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class ShortInBufferFilter extends ShortBufferFilter {

		private final Set<Short> set;

		public ShortInBufferFilter(RowIdSink rowIdSink, long startRowId, int startPosition, int count, ByteBuffer buffer, Set<Short> set) {
			super(rowIdSink, startRowId, startPosition, count, buffer);
			this.set = set;
		}

		@Override
		public void apply() {	
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							final short value = buffer.getShort(position);
							if(set.contains(value)) {
					rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
	
	public static abstract class SimpleShortBufferFilter extends ShortBufferFilter {
		
		protected final short value;

		public SimpleShortBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, short value) {
			super(rowIdSink, startRowId, position, count, buffer);
			this.value = value;
		}
	}
	
	public static final class ShortEqBufferFilter extends SimpleShortBufferFilter {

		public ShortEqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, short value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			long rowId = startRowId;
			int position = startPosition;
			for (int i = 0; i < count; i++) {
							if(buffer.getShort(position) == value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class ShortNeqBufferFilter extends SimpleShortBufferFilter {

		public ShortNeqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, short value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getShort(position) != value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class ShortLtBufferFilter extends SimpleShortBufferFilter {

		public ShortLtBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, short value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getShort(position) < value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class ShortLtEqBufferFilter extends SimpleShortBufferFilter {

		public ShortLtEqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, short value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getShort(position) <= value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class ShortGtBufferFilter extends SimpleShortBufferFilter {

		public ShortGtBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, short value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getShort(position) > value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
	
	public static final class ShortGtEqBufferFilter extends SimpleShortBufferFilter {

		public ShortGtEqBufferFilter(RowIdSink rowIdSink, long startRowId, int position, int count, ByteBuffer buffer, short value) {
			super(rowIdSink, startRowId, position, count, buffer, value);
		}

		@Override
		public void apply() {
			int position = startPosition;
			long rowId = startRowId;
			for (int i = 0; i < count; i++) {
							if(buffer.getShort(position) >= value) {
								rowIdSink.onRowId(rowId);
				}
				position += Type.SHORT.getWidth();
				++rowId;
			}
		}
	}
}