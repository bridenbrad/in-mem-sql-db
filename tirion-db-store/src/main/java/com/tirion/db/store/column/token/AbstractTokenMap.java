/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column.token;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.type.Type;
import com.tirion.db.store.column.Column;
import com.tirion.common.runtime.Runtime;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractTokenMap<TokenType, ValueType> implements TokenMap<TokenType, ValueType> {

	private final Runtime runtime;
	@JsonProperty
	private final Type valueType;
	private final Column column;

	public AbstractTokenMap(Type valueType, Column column, Runtime runtime) {
		super();
		this.valueType = valueType;
		this.column = column;
		this.runtime = runtime;
	}
	
	protected final Column getColumn() {
		return column;
	}

	protected final Runtime getRuntime() {
		return runtime;
	}

	@Override
	public final Type getValueType() {
		return valueType;
	}

	@Override
	public final Set<TokenType> getTokensFor(Set<ValueType> values, boolean strict) {
		Set<TokenType> set = new HashSet<TokenType>();
		for(ValueType value : values) {
			set.add(getTokenFor(value, strict));
		}
		return set;
	}

	@Override
	public final Set<ValueType> getValuesFor(Set<TokenType> tokens, boolean strict) {
		Set<ValueType> set = new HashSet<ValueType>();
		for(TokenType token : tokens) {
			set.add(getValueFor(token, strict));
		}
		return set;
	}	
	
//	@Override
//	public final Summary getSummary() {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("valueType", valueType.toString());
//		map.put("tokenType", getTokenType());
//		map.put("tokenToValueMapping", getMap());
//		return new SimpleSummary(map);
//	}

	/**
	 * For profiling only. Default returns null.
	 */
	@SuppressWarnings("rawtypes")
	protected Map getMap() {
		return null;
	}
}
