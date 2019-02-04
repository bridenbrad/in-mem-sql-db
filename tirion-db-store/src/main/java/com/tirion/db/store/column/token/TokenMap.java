/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column.token;

import java.util.Set;

import com.tirion.common.SizeAware;
import com.tirion.common.type.Type;
import com.tirion.db.store.appender.Appendable;

/**
 * Will throw exception at runtime if token cardinality
 * is violated.<p>
 * 
 * TODO add bulk methods for more efficient locking
 * TODO token map should be {@link Appendable}
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TokenMap<TokenType, ValueType> extends SizeAware {

	Type getTokenType();
	
	Type getValueType();
	
	long getMaxCardinality();
	
	/**
	 * If true, and no mapping is found, exception is thrown.
	 * If false, and not mapping is found, null is returned.
	 */
	TokenType getTokenFor(ValueType value, boolean strict);
	
	Set<TokenType> getTokensFor(Set<ValueType> values, boolean strict);
	
	/**
	 * Allocate new token if one is not already associated with given value.
	 */
	TokenType getTokenForAllocate(ValueType value);
	
	ValueType getValueFor(TokenType token, boolean strict);
	 
	Set<ValueType> getValuesFor(Set<TokenType> tokens, boolean strict);
	
	boolean hasToken(TokenType token);
	
	boolean hasValue(ValueType value);
	
	int getEntryCount();
	
	ProxyTokenMap<TokenType, ValueType> newProxy();
}
