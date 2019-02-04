/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.pool;

import java.util.Set;

import com.tirion.common.Lifecycle;
import com.tirion.common.SizeAware;
import com.tirion.common.sequence.array.Array;

/**
 * Pool of strings. For whatever reason, intern()-ing strings
 * is very slow.
 */
public interface Pool extends Lifecycle, SizeAware {
	
	public static final long NULL_TOKEN = 0;
	
	boolean hasToken(long token);
	
	/**
	 * Get UUID assigned to given string.
	 */
	long getToken(String value);
	
	boolean hasValue(String value);
	
	/**
	 * Get string assigned to given UUID.
	 */
	String getValue(long token);
	
	Set<Long> getTokens(Set<String> values);
	
	/**
	 * Map arrays of strings into array of longs.
	 */
	Array mapToLong(Array array);
}
