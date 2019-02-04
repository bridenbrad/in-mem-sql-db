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

import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatBetweenArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatGtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatGtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatInArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatLtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatLtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.FloatArrayFilter.FloatNeqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatBetweenBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatGtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatGtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatInBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatLtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatLtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.FloatBufferFilter.FloatNeqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.PageAwareRowIdSink;
import com.tirion.db.store.page.Page;

public abstract class FloatPageFilter extends AbstractPageFilter {

	public FloatPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position) {
		super(rowIdSink, page, filterRowIds, position);	
	}
	
	@Override
	public final void apply() {
		rowIdSink.before(page);
		if(filterRowIds != null) {
			applyFiltered();
		} else  {		
			applyNonFiltered(page.getUnderlying(isTokenized()));
		}
		rowIdSink.after(page);
	}
	
	protected abstract void applyNonFiltered(Object underlying);
	protected abstract void applyFiltered();
	
	public static final class FloatInPageFilter extends FloatPageFilter {
		
		private final Set<Float> set;

		public FloatInPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, Set<Float> set) {
			super(rowIdSink, page, filterRowIds, position);
			this.set = set;
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatInBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, set).apply();
			} else {
				new FloatInArrayFilter(rowIdSink, page.getStartRowId(), (float[])underlying, set).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && set.contains(cell)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class FloatBetweenPageFilter extends FloatPageFilter {

		private final float low;
		private final float high;
		
		public FloatBetweenPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float low, float high) {
			super(rowIdSink, page, filterRowIds, position);
			this.low = low;
			this.high = high;
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatBetweenBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, low, high).apply();
			} else {
				new FloatBetweenArrayFilter(rowIdSink, page.getStartRowId(), (float[])underlying, low, high).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && (low <= cell.floatValue() && cell.floatValue() <= high)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}

	public static abstract class SimpleFloatPageFilter extends FloatPageFilter {
		
		protected final float value;
		
		public SimpleFloatPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float value) {
			super(rowIdSink, page, filterRowIds, position);
			this.value = value;
		}
	}
	
	public static final class FloatEqPageFilter extends SimpleFloatPageFilter {

		public FloatEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new FloatEqArrayFilter(rowIdSink, page.getStartRowId(), (float[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && cell.floatValue() == value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class FloatNeqPageFilter extends SimpleFloatPageFilter {

		public FloatNeqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatNeqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new FloatNeqArrayFilter(
						rowIdSink, page.getStartRowId(), (float[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && cell.floatValue() != value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class FloatLtPageFilter extends SimpleFloatPageFilter {

		public FloatLtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatLtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new FloatLtArrayFilter(
						rowIdSink, page.getStartRowId(), (float[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && cell.floatValue() < value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class FloatLtEqPageFilter extends SimpleFloatPageFilter {

		public FloatLtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatLtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new FloatLtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (float[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && cell.floatValue() <= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class FloatGtPageFilter extends SimpleFloatPageFilter {

		public FloatGtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatGtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new FloatGtArrayFilter(
						rowIdSink, page.getStartRowId(), (float[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && cell.floatValue() > value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class FloatGtEqPageFilter extends SimpleFloatPageFilter {

		public FloatGtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, float value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered(Object underlying) {
			if(underlying instanceof ByteBuffer) {
				new FloatGtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new FloatGtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (float[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Float cell = (Float) values.get(i);
				if(cell != null && cell.floatValue() >= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
}
