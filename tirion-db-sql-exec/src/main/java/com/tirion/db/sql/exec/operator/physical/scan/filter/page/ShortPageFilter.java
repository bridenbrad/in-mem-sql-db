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

import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortBetweenArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortGtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortGtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortInArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortLtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortLtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.ShortArrayFilter.ShortNeqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortBetweenBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortGtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortGtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortInBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortLtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortLtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.ShortBufferFilter.ShortNeqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.PageAwareRowIdSink;
import com.tirion.db.store.page.Page;

public abstract class ShortPageFilter extends AbstractPageFilter {

	public ShortPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position) {
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
	
	public static final class ShortInPageFilter extends ShortPageFilter {
		
		private final Set<Short> set;

		public ShortInPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, Set<Short> set) {
			super(rowIdSink, page, filterRowIds, position);
			this.set = set;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortInBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, set).apply();
			} else {
				new ShortInArrayFilter(rowIdSink, page.getStartRowId(), (short[])underlying, set).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && set.contains(cell)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ShortBetweenPageFilter extends ShortPageFilter {

		private final short low;
		private final short high;
		
		public ShortBetweenPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short low, short high) {
			super(rowIdSink, page, filterRowIds, position);
			this.low = low;
			this.high = high;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortBetweenBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, low, high).apply();
			} else {
				new ShortBetweenArrayFilter(rowIdSink, page.getStartRowId(), (short[])underlying, low, high).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && (low <= cell.shortValue() && cell.shortValue() <= high)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}

	public static abstract class SimpleShortPageFilter extends ShortPageFilter {
		
		protected final short value;
		
		public SimpleShortPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short value) {
			super(rowIdSink, page, filterRowIds, position);
			this.value = value;
		}
	}
	
	public static final class ShortEqPageFilter extends SimpleShortPageFilter {

		public ShortEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ShortEqArrayFilter(rowIdSink, page.getStartRowId(), (short[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && cell.shortValue() == value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ShortNeqPageFilter extends SimpleShortPageFilter {

		public ShortNeqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortNeqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ShortNeqArrayFilter(
						rowIdSink, page.getStartRowId(), (short[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && cell.shortValue() != value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ShortLtPageFilter extends SimpleShortPageFilter {

		public ShortLtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortLtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ShortLtArrayFilter(
						rowIdSink, page.getStartRowId(), (short[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && cell.shortValue() < value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ShortLtEqPageFilter extends SimpleShortPageFilter {

		public ShortLtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortLtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ShortLtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (short[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && cell.shortValue() <= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ShortGtPageFilter extends SimpleShortPageFilter {

		public ShortGtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortGtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ShortGtArrayFilter(
						rowIdSink, page.getStartRowId(), (short[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && cell.shortValue() > value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class ShortGtEqPageFilter extends SimpleShortPageFilter {

		public ShortGtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, short value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new ShortGtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new ShortGtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (short[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Short cell = (Short) values.get(i);
				if(cell != null && cell.shortValue() >= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
}
