/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column.token;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tirion.common.runtime.Runtime;
import com.tirion.common.type.Type;
import com.tirion.db.store.column.Column;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BaseTokenMap<TokenType, ValueType> extends AbstractTokenMap<TokenType, ValueType> {

	private final Map<TokenType, ValueType> tokenToValue;
	private final Map<ValueType, TokenType> valueToToken;
	
	public BaseTokenMap(Type valueType, Column column, Runtime runtime) {
		super(valueType, column, runtime);
		tokenToValue = new HashMap<TokenType, ValueType>();
		valueToToken = new HashMap<ValueType, TokenType>();
	}
	
	@Override
	public final long sizeInBytes() {
		int count = tokenToValue.size();
		return 2 * (count * 8 * 2); // TODO how to calculate strings?
	}

	@Override
	public final boolean hasToken(TokenType token) {
		return tokenToValue.containsKey(token);
	}

	@Override
	public final boolean hasValue(ValueType value) {
		return valueToToken.containsKey(value);
	}
	
	@Override
	public final TokenType getTokenFor(ValueType value, boolean strict) {
		TokenType token = valueToToken.get(value);
		if(token == null && strict) {
			throw new IllegalStateException("No token mapped to value '" + value + "'");
		}
		return token;
	}
	
	@Override
	public final TokenType getTokenForAllocate(ValueType value) {
		TokenType token = valueToToken.get(value);
		if(token == null) {
			token = allocateNextToken();
			valueToToken.put(value, token);
			tokenToValue.put(token, value);
		}
		return token;
	}

	@Override
	public final ValueType getValueFor(TokenType token, boolean strict) {
		ValueType value = tokenToValue.get(token);
		if(value == null && strict) {
			throw new IllegalArgumentException("Unable to resolve token " + token + " to value");
		}
		return value;
	}
	
	@Override
	public final int getEntryCount() {
		return tokenToValue.size();
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected Map getMap() {
		return tokenToValue;
	}
	
	protected abstract TokenType allocateNextToken();
	
	protected abstract class BaseTokenMapProxy extends AbstractTokenMap<TokenType, ValueType> implements ProxyTokenMap<TokenType, ValueType> {
		
		private final Map<TokenType, ValueType> t2v;
		private final Map<ValueType, TokenType> v2t;

		public BaseTokenMapProxy(Type valueType, Column column, Runtime runtime) {
			super(valueType, column, runtime);
			t2v = new HashMap<TokenType, ValueType>();
			v2t = new HashMap<ValueType, TokenType>();
		}

		@Override
		public final void merge() {
			for(Entry<TokenType, ValueType> entry : t2v.entrySet()) {
				if(BaseTokenMap.this.tokenToValue.put(entry.getKey(), entry.getValue()) != null) {
					throw new IllegalStateException("Token '" + entry.getKey() + "' was already present for value '" + entry.getValue() + "'");
				}
				if(BaseTokenMap.this.valueToToken.put(entry.getValue(), entry.getKey()) != null) {
					throw new IllegalStateException("Token '" + entry.getKey() + "' was already present for value '" + entry.getValue() + "'");
				}
				onMergeComplete();
			}				
		}

		@Override
		public final TokenType getTokenFor(ValueType value, boolean strict) {
			TokenType token = v2t.get(value);
			if(token != null) {
				return token;				
			}
			return BaseTokenMap.this.getTokenFor(value, strict);
		}

		@Override
		public final TokenType getTokenForAllocate(ValueType value) {
			TokenType token = v2t.get(value);
			if(token != null) {
				return token;
			}
			token = BaseTokenMap.this.getTokenFor(value, false);
			if(token != null) {
				return token;
			}
			token = allocateNextToken();
			v2t.put(value, token);
			t2v.put(token, value);
			return token;
		}

		@Override
		public final ValueType getValueFor(TokenType token, boolean strict) {
			ValueType value = t2v.get(token);
			if(value != null) {
				return value;
			}
			return BaseTokenMap.this.getValueFor(token, strict);
		}

		@Override
		public final int getEntryCount() {
			return BaseTokenMap.this.getEntryCount() + t2v.size();
		}
		
		@Override
		public final Type getTokenType() {
			return BaseTokenMap.this.getTokenType();
		}

		@Override
		public final long getMaxCardinality() {
			return BaseTokenMap.this.getMaxCardinality();
		}
		
		protected abstract TokenType allocateNextToken();
		/**
		 * Called to indicate that proxy has been merged into
		 * main map.
		 */
		protected abstract void onMergeComplete();

		@Override
		public final ProxyTokenMap<TokenType, ValueType> newProxy() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final long sizeInBytes() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public final boolean hasToken(TokenType token) {
			throw new UnsupportedOperationException();
		}

		@Override
		public final boolean hasValue(ValueType value) {
			throw new UnsupportedOperationException();
		}

	}
}
