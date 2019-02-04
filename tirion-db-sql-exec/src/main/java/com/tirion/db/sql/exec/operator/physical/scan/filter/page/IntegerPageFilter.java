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

import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerBetweenArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerGtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerGtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerInArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerLtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerLtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.IntegerArrayFilter.IntegerNeqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerBetweenBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerGtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerGtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerInBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerLtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerLtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.IntegerBufferFilter.IntegerNeqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.PageAwareRowIdSink;
import com.tirion.db.store.page.Page;

public abstract class IntegerPageFilter extends AbstractPageFilter {

	public IntegerPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position) {
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
	
	public static final class IntegerInPageFilter extends IntegerPageFilter {
		
		private final Set<Integer> set;

		public IntegerInPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, Set<Integer> set) {
			super(rowIdSink, page, filterRowIds, position);
			this.set = set;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerInBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, set).apply();
			} else {
				new IntegerInArrayFilter(rowIdSink, page.getStartRowId(), (int[])underlying, set).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && set.contains(cell)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class IntegerBetweenPageFilter extends IntegerPageFilter {

		private final int low;
		private final int high;
		
		public IntegerBetweenPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int low, int high) {
			super(rowIdSink, page, filterRowIds, position);
			this.low = low;
			this.high = high;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerBetweenBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, low, high).apply();
			} else {
				new IntegerBetweenArrayFilter(rowIdSink, page.getStartRowId(), (int[])underlying, low, high).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && (low <= cell.intValue() && cell.intValue() <= high)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}

	public static abstract class SimpleIntegerPageFilter extends IntegerPageFilter {
		
		protected final int value;
		
		public SimpleIntegerPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int value) {
			super(rowIdSink, page, filterRowIds, position);
			this.value = value;
		}
	}
	
	public static final class IntegerEqPageFilter extends SimpleIntegerPageFilter {

		public IntegerEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new IntegerEqArrayFilter(rowIdSink, page.getStartRowId(), (int[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && cell.intValue() == value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class IntegerNeqPageFilter extends SimpleIntegerPageFilter {

		public IntegerNeqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerNeqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new IntegerNeqArrayFilter(
						rowIdSink, page.getStartRowId(), (int[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && cell.intValue() != value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class IntegerLtPageFilter extends SimpleIntegerPageFilter {

		public IntegerLtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerLtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new IntegerLtArrayFilter(
						rowIdSink, page.getStartRowId(), (int[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && cell.intValue() < value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class IntegerLtEqPageFilter extends SimpleIntegerPageFilter {

		public IntegerLtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerLtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new IntegerLtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (int[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && cell.intValue() <= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class IntegerGtPageFilter extends SimpleIntegerPageFilter {

		public IntegerGtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerGtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new IntegerGtArrayFilter(
						rowIdSink, page.getStartRowId(), (int[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && cell.intValue() > value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class IntegerGtEqPageFilter extends SimpleIntegerPageFilter {

		public IntegerGtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, int value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new IntegerGtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new IntegerGtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (int[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Integer cell = (Integer) values.get(i);
				if(cell != null && cell.intValue() >= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
}
