/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter.page;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteBetweenArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteGtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteGtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteInArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteLtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteLtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ByteArrayFilter.ByteNeqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteBetweenBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteGtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteGtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteInBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteLtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteLtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ByteBufferFilter.ByteNeqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.PageAwareRowIdSink;
import com.tirion.db.store.page.Page;

public abstract class BytePageFilter extends AbstractPageFilter {

	public BytePageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position) {
		super(rowIdSink, page, filterRowIds, position);	
	}
	
	@Override
	public final void apply() {
		rowIdSink.before(page);
		if(filterRowIds != null) {
			applyFiltered();
		} else  {
			applyNonFiltered();
		}
		rowIdSink.after(page);
	}
	
	protected abstract void applyNonFiltered();
	protected abstract void applyFiltered();
	
	public static final class ByteInPageFilter extends BytePageFilter {
		
		private final Set<Byte> set;

		public ByteInPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, Set<Byte> set) {
			super(rowIdSink, page, filterRowIds, position);
			this.set = set;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteInBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, set).apply();
			} else {
				new ByteInArrayFilter(rowIdSink, page.getStartRowId(), (byte[])underlying, set).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && set.contains(cell)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ByteBetweenPageFilter extends BytePageFilter {

		private final byte low;
		private final byte high;
		
		public ByteBetweenPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte low, byte high) {
			super(rowIdSink, page, filterRowIds, position);
			this.low = low;
			this.high = high;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteBetweenBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, low, high).apply();
			} else {
				new ByteBetweenArrayFilter(rowIdSink, page.getStartRowId(), (byte[])underlying, low, high).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && (low <= cell.byteValue() && cell.byteValue() <= high)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}

	public static abstract class SimpleBytePageFilter extends BytePageFilter {
		
		protected final byte value;
		
		public SimpleBytePageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte value) {
			super(rowIdSink, page, filterRowIds, position);
			this.value = value;
		}
	}
	
	public static final class ByteEqPageFilter extends SimpleBytePageFilter {

		public ByteEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ByteEqArrayFilter(rowIdSink, page.getStartRowId(), (byte[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && cell.byteValue() == value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ByteNeqPageFilter extends SimpleBytePageFilter {

		public ByteNeqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteNeqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ByteNeqArrayFilter(
						rowIdSink, page.getStartRowId(), (byte[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && cell.byteValue() != value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ByteLtPageFilter extends SimpleBytePageFilter {

		public ByteLtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteLtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ByteLtArrayFilter(
						rowIdSink, page.getStartRowId(), (byte[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && cell.byteValue() < value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ByteLtEqPageFilter extends SimpleBytePageFilter {

		public ByteLtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteLtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ByteLtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (byte[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && cell.byteValue() <= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ByteGtPageFilter extends SimpleBytePageFilter {

		public ByteGtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteGtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ByteGtArrayFilter(
						rowIdSink, page.getStartRowId(), (byte[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && cell.byteValue() > value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ByteGtEqPageFilter extends SimpleBytePageFilter {

		public ByteGtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, byte value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ByteGtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ByteGtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (byte[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Byte cell = (Byte) values.get(i);
				if(cell != null && cell.byteValue() >= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
}
