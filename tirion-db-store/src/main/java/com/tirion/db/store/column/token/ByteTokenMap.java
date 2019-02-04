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
public final class ByteTokenMap<ValueType> extends BaseTokenMap<Byte, ValueType> {

	@JsonProperty
	private byte counter = 0;

	public ByteTokenMap(Type valueType, Column column, Runtime runtime) {
		super(valueType, column, runtime);
	}
	
	@Override
	public long getMaxCardinality() {
		return Constants.BYTE_DISTINCT_COUNT;
	}
	
	@JsonProperty
	@Override
	public Type getTokenType() {
		return Type.BYTE;
	}

	@Override
	public ProxyTokenMap<Byte, ValueType> newProxy() {
		return new ByteTokenMapProxy(getValueType(), getColumn(), getRuntime());
	}

	@Override
	protected Byte allocateNextToken() {
		if(counter == -1) {
			throw new IllegalStateException("Overflow of token map for column '" + getColumn().getName() + "'");
		}
		return counter++;
	}

	private final class ByteTokenMapProxy extends BaseTokenMapProxy {
		
		private byte ctr;

		public ByteTokenMapProxy(Type valueType, Column column, Runtime runtime) {
			super(valueType, column, runtime);
			ctr = ByteTokenMap.this.counter;
		}

		@Override
		protected Byte allocateNextToken() {
			if(ctr == -1) {
				throw new IllegalStateException("Loading of new data set will cause overflow of token map for column '" + getColumn().getName() + "'");
			}
			return ctr++;
		}

		@Override
		protected void onMergeComplete() {
			ByteTokenMap.this.counter = ctr;
		}
	}
}
