/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector.extractor;

import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.Util;
import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.store.page.Page;
import com.tirion.db.store.page.finder.PageFinder;
import com.tirion.pool.Pool;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractValueExtractor implements ValueExtractor {

	@JsonProperty
	private final int targeTupleIndex;
	private final PageFinder pageFinder;
	
	public AbstractValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
		this.targeTupleIndex = targeTupleIndex;
		this.pageFinder = pageFinder;
	}
	
	@Override
	public final void exec(long rowId, Tuple tuple) {
		Object value = pageFinder.findPage(rowId).getValue(rowId);
		if(value == null) {
			tuple.setNull(targeTupleIndex);
		} else {			
			execInternal(value, tuple);
		}
	}
	
	@Override
	public final void exec(List<Long> rowIds, List<Tuple> tuples) {
		Util.assertTrue(rowIds.size() == tuples.size());
		if(rowIds.isEmpty()) {
			return;
		}
		Page page = pageFinder.findPage(rowIds.get(0));
		List<Object> values = page.getValues(rowIds);
		Iterator<Tuple> it = tuples.iterator();
		for (int i = 0; i < values.size(); i++) {
			Object value = values.get(i);
			Tuple tuple = it.next();
			if(value == null) {
				tuple.setNull(targeTupleIndex);
			} else {			
				execInternal(value, tuple);
			}
		}
	}

	protected final int getTargeTupleIndex() {
		return targeTupleIndex;
	}

	protected abstract void execInternal(Object value, Tuple tuple);
	protected abstract Type getType();

	public static final class BooleanValueExtractor extends AbstractValueExtractor {

		public BooleanValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
			super(targeTupleIndex, pageFinder);
		}
		
		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putBoolean(getTargeTupleIndex(), (Boolean)value);			
		}

		@Override
		@JsonProperty
		protected Type getType() {
			return Type.BOOLEAN;
		}		
	}
	
	public static final class ByteValueExtractor extends AbstractValueExtractor {

		public ByteValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
			super(targeTupleIndex, pageFinder);
		}
		
		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putByte(getTargeTupleIndex(), (Byte)value);			
		}		
		
		@Override
		@JsonProperty
		protected Type getType() {
			return Type.BYTE;
		}
	}

	public static final class ShortValueExtractor extends AbstractValueExtractor {

		public ShortValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
			super(targeTupleIndex, pageFinder);
		}

		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putShort(getTargeTupleIndex(), (Short)value);			
		}
		
		@Override
		@JsonProperty
		protected Type getType() {
			return Type.SHORT;
		}
	}
	
	public static final class IntValueExtractor extends AbstractValueExtractor {

		public IntValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
			super(targeTupleIndex, pageFinder);
		}

		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putInteger(getTargeTupleIndex(), (Integer)value);			
		}
		
		@Override
		@JsonProperty
		protected Type getType() {
			return Type.INT;
		}
	}
	
	public static final class LongValueExtractor extends AbstractValueExtractor {

		public LongValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
			super(targeTupleIndex, pageFinder);
		}

		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putLong(getTargeTupleIndex(), (Long)value);			
		}
		
		@Override
		@JsonProperty
		protected Type getType() {
			return Type.LONG;
		}
	}
	
	public static final class FloatValueExtractor extends AbstractValueExtractor {
		
		public FloatValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
			super(targeTupleIndex, pageFinder);
		}

		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putFloat(getTargeTupleIndex(), (Float)value);			
		}
		
		@Override
		@JsonProperty
		protected Type getType() {
			return Type.FLOAT;
		}
	}
	
	public static final class DoubleValueExtractor extends AbstractValueExtractor {

		public DoubleValueExtractor(int targeTupleIndex, PageFinder pageFinder) {
			super(targeTupleIndex, pageFinder);
		}

		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putDouble(getTargeTupleIndex(), (Double)value);			
		}
		
		@Override
		@JsonProperty
		protected Type getType() {
			return Type.DOUBLE;
		}
	}
	
	public static final class StringValueExtractor extends AbstractValueExtractor {

		private final Pool pool;
		
		public StringValueExtractor(int targeTupleIndex, PageFinder pageFinder, Runtime runtime) {
			super(targeTupleIndex, pageFinder);
//			this.pool = runtime.pool();
			throw new NotYetImplementedException();
		}

		@Override
		protected void execInternal(Object value, Tuple tuple) {
			tuple.putString(getTargeTupleIndex(), pool.getValue((Long)value));
		}
		
		@Override
		@JsonProperty
		protected Type getType() {
			return Type.VARCHAR;
		}
	}	
}
