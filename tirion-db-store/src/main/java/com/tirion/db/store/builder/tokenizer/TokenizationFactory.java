/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.tokenizer;

import com.tirion.common.Constants;
import com.tirion.common.Util;
import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.store.builder.tokenizer.DoubleTokenizer.DoubleToByteTokenizer;
import com.tirion.db.store.builder.tokenizer.DoubleTokenizer.DoubleToShortTokenizer;
import com.tirion.db.store.builder.tokenizer.FloatTokenizer.FloatToByteTokenizer;
import com.tirion.db.store.builder.tokenizer.FloatTokenizer.FloatToShortTokenizer;
import com.tirion.db.store.builder.tokenizer.IntegerTokenizer.IntegerToByteTokenizer;
import com.tirion.db.store.builder.tokenizer.IntegerTokenizer.IntegerToShortTokenizer;
import com.tirion.db.store.builder.tokenizer.LongTokenizer.LongToByteTokenizer;
import com.tirion.db.store.builder.tokenizer.LongTokenizer.LongToShortTokenizer;
import com.tirion.db.store.builder.tokenizer.ShortTokenizer.ShortToByteTokenizer;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.column.token.ByteTokenMap;
import com.tirion.db.store.column.token.ShortTokenMap;
import com.tirion.db.store.column.token.TokenMap;
import com.tirion.common.runtime.Runtime;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class TokenizationFactory {
	
	public static boolean shouldTokenize(Field field) {
		if(field.getOptions() == null || !field.getOptions().isTokenized()) {
			return false;
		}
		return tokenTypeFor(field.getOptions().getDistinct(), field.getNativeType()) != null;
	}
	
	/**
	 * May return null in case given parameters can not be tokenized.
	 */
	public static Type tokenTypeFor(long cardinality, Type valueType) {
		Util.assertTrue(valueType.isNative());
		if(valueType == Type.BYTE || valueType == Type.BOOLEAN) {
			return null;
		}else if(cardinality <= Constants.BYTE_DISTINCT_COUNT) {
			return Type.BYTE;
		} else if(cardinality <= Constants.SHORT_DISTINCT_COUNT && valueType != Type.SHORT) {
			return Type.SHORT;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static TokenMap<Object, Object> newTokenMap(Type tokenType, Column column, Runtime runtime) {
		switch (tokenType) {
			case BYTE:
				return (TokenMap)(new ByteTokenMap<Object>(column.getType(), column, runtime));
			case SHORT:
				return (TokenMap)(new ShortTokenMap<Object>(column.getType(), column, runtime));
			default:
				throw new IllegalArgumentException(column.getTokenType().toString());
		}
	}
	
	/**
	 * Creates new instance of tokenizer.
	 */
	@SuppressWarnings("unchecked")
	public static Tokenizer newTokenizer(Type tokenType, Type valueType, TokenMap<?, ?> map) {
		switch (valueType) {
			case SHORT:
				{
					Util.assertTrue(tokenType == Type.BYTE);
					return new ShortToByteTokenizer((TokenMap<Byte, Short>)map);
				}
			case DATE:
				{
					Util.assertTrue(tokenType == Type.BYTE);
					return new ShortToByteTokenizer((TokenMap<Byte, Short>)map);
				}
			case TIME:
			case INT:
				{
					if(tokenType == Type.BYTE) {
						return new IntegerToByteTokenizer((TokenMap<Byte, Integer>)map);
					} else if(tokenType == Type.SHORT) {
						return new IntegerToShortTokenizer((TokenMap<Short, Integer>)map);
					} else {
						throw new IllegalArgumentException("Unexpected token type '" + tokenType + "'");
					}
				}
			case TIMESTAMP:
			case LONG:
				{
					if(tokenType == Type.BYTE) {
						return new LongToByteTokenizer((TokenMap<Byte, Long>)map);
					} else if(tokenType == Type.SHORT) {
						return new LongToShortTokenizer((TokenMap<Short, Long>)map);
					} else {
						throw new IllegalArgumentException("Unexpected token type '" + tokenType + "'");
					}
				}
			case FLOAT:
				{
					if(tokenType == Type.BYTE) {
						return new FloatToByteTokenizer((TokenMap<Byte, Float>)map);
					} else if(tokenType == Type.SHORT) {
						return new FloatToShortTokenizer((TokenMap<Short, Float>)map);
					} else {
						throw new IllegalArgumentException("Unexpected token type '" + tokenType + "'");
					}
				}
			case DOUBLE:
				{
					if(tokenType == Type.BYTE) {
						return new DoubleToByteTokenizer((TokenMap<Byte, Double>)map);
					} else if(tokenType == Type.SHORT) {
						return new DoubleToShortTokenizer((TokenMap<Short, Double>)map);
					} else {
						throw new IllegalArgumentException("Unexpected token type '" + tokenType + "'");
					}
				}
			default:
				throw new UnsupportedOperationException("Tokenization of type '" + valueType + "' is not supported");
		}
	}
	
	private TokenizationFactory() {
	}
}
