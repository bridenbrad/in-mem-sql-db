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

import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleBetweenArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleGtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleGtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleInArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleLtArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleLtEqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.array.DoubleArrayFilter.DoubleNeqArrayFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleBetweenBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleGtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleGtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleInBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleLtBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleLtEqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.filter.buffer.DoubleBufferFilter.DoubleNeqBufferFilter;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.PageAwareRowIdSink;
import com.tirion.db.store.page.Page;

public abstract class DoublePageFilter extends AbstractPageFilter {

	public DoublePageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position) {
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
	
	public static final class DoubleInPageFilter extends DoublePageFilter {
		
		private final Set<Double> set;

		public DoubleInPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, Set<Double> set) {
			super(rowIdSink, page, filterRowIds, position);
			this.set = set;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleInBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, set).apply();
			} else {
				new DoubleInArrayFilter(rowIdSink, page.getStartRowId(), (double[])underlying, set).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && set.contains(cell)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class DoubleBetweenPageFilter extends DoublePageFilter {

		private final double low;
		private final double high;
		
		public DoubleBetweenPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double low, double high) {
			super(rowIdSink, page, filterRowIds, position);
			this.low = low;
			this.high = high;
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleBetweenBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, low, high).apply();
			} else {
				new DoubleBetweenArrayFilter(rowIdSink, page.getStartRowId(), (double[])underlying, low, high).apply();
			}
		}

		@Override
		protected void applyFiltered() {
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && (low <= cell.doubleValue() && cell.doubleValue() <= high)) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}

	public static abstract class SimpleDoublePageFilter extends DoublePageFilter {
		
		protected final double value;
		
		public SimpleDoublePageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double value) {
			super(rowIdSink, page, filterRowIds, position);
			this.value = value;
		}
	}
	
	public static final class DoubleEqPageFilter extends SimpleDoublePageFilter {

		public DoubleEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new DoubleEqArrayFilter(rowIdSink, page.getStartRowId(), (double[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && cell.doubleValue() == value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class DoubleNeqPageFilter extends SimpleDoublePageFilter {

		public DoubleNeqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleNeqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new DoubleNeqArrayFilter(
						rowIdSink, page.getStartRowId(), (double[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && cell.doubleValue() != value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class DoubleLtPageFilter extends SimpleDoublePageFilter {

		public DoubleLtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleLtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new DoubleLtArrayFilter(
						rowIdSink, page.getStartRowId(), (double[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && cell.doubleValue() < value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class DoubleLtEqPageFilter extends SimpleDoublePageFilter {

		public DoubleLtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleLtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new DoubleLtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (double[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && cell.doubleValue() <= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class DoubleGtPageFilter extends SimpleDoublePageFilter {

		public DoubleGtPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleGtBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new DoubleGtArrayFilter(
						rowIdSink, page.getStartRowId(), (double[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && cell.doubleValue() > value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
	
	public static final class DoubleGtEqPageFilter extends SimpleDoublePageFilter {

		public DoubleGtEqPageFilter(PageAwareRowIdSink rowIdSink, Page page, List<Long> filterRowIds, int position, double value) {
			super(rowIdSink, page, filterRowIds, position, value);
		}

		@Override
		protected void applyNonFiltered() {
			Object underlying = page.getUnderlying();
			if(underlying instanceof ByteBuffer) {
				new DoubleGtEqBufferFilter(rowIdSink, page.getStartRowId(), 
						position, page.getCount(), (ByteBuffer)underlying, value).apply();
			} else {
				new DoubleGtEqArrayFilter(
						rowIdSink, page.getStartRowId(), (double[])underlying, value).apply();
			}
		}
		
		@Override
		protected void applyFiltered() {			
			List<Object> values = page.getValues(filterRowIds);
			for (int i = 0; i < values.size(); i++) {
				final Double cell = (Double) values.get(i);
				if(cell != null && cell.doubleValue() >= value) {
					rowIdSink.onRowId(filterRowIds.get(i));
				}
			}
		}
	}
}
