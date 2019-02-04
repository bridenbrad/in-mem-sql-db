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

import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongBetweenArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongGtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongGtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongInArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongLtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongLtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.LongArrayFilter.LongNeqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongBetweenBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongGtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongGtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongInBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongLtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongLtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.LongBufferFilter.LongNeqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.PageAwareRowIdSink;
import com.tirion.db.store.page.Page;

public abstract class LongPageFilter extends AbstractPageFilter {

	public LongPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position) {
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
	
	public static final class LongInPageFilter extends LongPageFilter {
		
		private final Set<Long> set;

		public LongInPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, Set<Long> set) {
			super(rowIdSink, page, filterRowIds, position);
			this.set = set;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongInBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, set).apply();
			} else {
				new LongInArrayFilter(rowIdSink, page.getStartRowId(), (long[])underlying, set).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && set.contains(cell)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class LongBetweenPageFilter extends LongPageFilter {

		private final long low;
		private final long high;
		
		public LongBetweenPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long low, long high) {
			super(rowIdSink, page, filterRowIds, position);
			this.low = low;
			this.high = high;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongBetweenBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, low, high).apply();
			} else {
				new LongBetweenArrayFilter(rowIdSink, page.getStartRowId(), (long[])underlying, low, high).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && (low <= cell.longValue() && cell.longValue() <= high)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}

	public static abstract class SimpleLongPageFilter extends LongPageFilter {
		
		protected final long value;
		
		public SimpleLongPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long value) {
			super(rowIdSink, page, filterRowIds, position);
			this.value = value;
		}
	}
	
	public static final class LongEqPageFilter extends SimpleLongPageFilter {

		public LongEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new LongEqArrayFilter(rowIdSink, page.getStartRowId(), (long[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && cell.longValue() == value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class LongNeqPageFilter extends SimpleLongPageFilter {

		public LongNeqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongNeqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new LongNeqArrayFilter(
						rowIdSink, page.getStartRowId(), (long[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && cell.longValue() != value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class LongLtPageFilter extends SimpleLongPageFilter {

		public LongLtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongLtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new LongLtArrayFilter(
						rowIdSink, page.getStartRowId(), (long[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && cell.longValue() < value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class LongLtEqPageFilter extends SimpleLongPageFilter {

		public LongLtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongLtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new LongLtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (long[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && cell.longValue() <= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class LongGtPageFilter extends SimpleLongPageFilter {

		public LongGtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongGtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new LongGtArrayFilter(
						rowIdSink, page.getStartRowId(), (long[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && cell.longValue() > value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class LongGtEqPageFilter extends SimpleLongPageFilter {

		public LongGtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, long value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new LongGtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new LongGtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (long[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Long cell = (Long) values.get(i);
				if(cell != null && cell.longValue() >= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
}
