/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column.token;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.Constants;
import com.tirion.common.type.Type;
import com.tirion.db.store.column.Column;
import com.tirion.common.runtime.Runtime;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ShortTokenMap<ValueType> extends BaseTokenMap<Short, ValueType> {

	@JsonProperty
	private short counter = 0;

	public ShortTokenMap(Type valueType, Column column, Runtime runtime) {
		super(valueType, column, runtime);
	}
	
	@Override
	public long getMaxCardinality() {
		return Constants.SHORT_DISTINCT_COUNT;
	}
	
	@JsonProperty
	@Override
	public Type getTokenType() {
		return Type.SHORT;
	}

	@Override
	public ProxyTokenMap<Short, ValueType> newProxy() {
		return new ShortTokenMapProxy(getValueType(), getColumn(), getRuntime());
	}

	@Override
	protected Short allocateNextToken() {
		if(counter == -1) {
			throw new IllegalStateException("Overflow of token map for column '" + getColumn().getName() + "'");
		}
		return counter++;
	}

	private final class ShortTokenMapProxy extends BaseTokenMapProxy {
		
		private short ctr;

		public ShortTokenMapProxy(Type valueType, Column column, Runtime runtime) {
			super(valueType, column, runtime);
			ctr = ShortTokenMap.this.counter;
		}

		@Override
		protected Short allocateNextToken() {
			if(ctr == -1) {
				throw new IllegalStateException("Loading of new data set will cause overflow of token map for column '" + getColumn().getName() + "'");
			}
			return ctr++;
		}
		
		@Override
		protected void onMergeComplete() {
			ShortTokenMap.this.counter = ctr;
		}
	}
}
