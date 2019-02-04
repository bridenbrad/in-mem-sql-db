/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.pool;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.tirion.common.metrics.Metrics;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.array.NativeArray;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimplePool implements Pool { // TODO more efficient concurrency

	private long counter = 1;
	private final ConcurrentHashMap<String, Long> strToLong;
	private final ConcurrentHashMap<Long, String> longToStr;
	
	private Counter charCount;
	
	public SimplePool() {
		strToLong = new ConcurrentHashMap<String, Long>();
		longToStr = new ConcurrentHashMap<Long, String>();
	}
	
	@Override
	public void init() {
		Metrics.get().register(MetricRegistry.name("pool", "entry-count"),
				new Gauge<Integer>() {
					@Override
					public Integer getValue() {
						return strToLong.size();
					}
				}
		);
		charCount = Metrics.get().counter(MetricRegistry.name("pool", "char-count"));
	}

	@Override
	public void shutdown() {
		strToLong.clear();
		longToStr.clear();
	}
	
//	@Override
//	public Summary getSummary() {
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("entry-count", strToLong.size());
//		result.put("string-to-token", Collections.unmodifiableMap(strToLong));
//		return new SimpleSummary(result);
//	}

	@Override
	public long sizeInBytes() {
		long stringSize = 0;
		for(String str : strToLong.keySet()) {
			stringSize += str.length();
		}
		return 2 * (strToLong.size() * 8 * 2) + 8 + stringSize;
	}
	
	@Override
	public boolean hasToken(long token) {
		if(token == NULL_TOKEN) {
			return true;
		}
		return longToStr.containsKey(token);
	}

	@Override
	public boolean hasValue(String value) {
		if(value == null) {
			return true;
		}
		return strToLong.containsKey(value);
	}

	@Override
	public String getValue(long id) {
		if(id == NULL_TOKEN) {
			return null;
		}
		String result = longToStr.get(id);
		if(result == null) {
			throw new IllegalArgumentException("No string mapped to id '" + id + "'");
		}
		return result;
	}

	@Override
	public long getToken(String str) {
		if(str == null) {
			return NULL_TOKEN;
		}
		Long value = strToLong.get(str);
		if(value == null) {
			value = registerNewMapping(str);
		}
		return value;
	}

	@Override
	public Set<Long> getTokens(Set<String> strings) {
		Set<Long> result = new HashSet<Long>(strings.size());
		for(String string : strings) {
			result.add(getToken(string));
		}
		return result;
	}

	@Override
	public Array mapToLong(Array array) {
		String[] inputArr = (String[]) array.getUnderlying();
		long[] outputArr = new long[inputArr.length];
		for (int i = 0; i < inputArr.length; i++) {
			if(!array.hasNullBitmap() || !array.getNullBitmap().isSet(i)) {				
				outputArr[i] = getToken(inputArr[i]); // TODO locking efficiency
			}
		}
		return new NativeArray(new Spec(Type.LONG, outputArr.length, false, array.getNullBitmap()), outputArr);
	}
	
	private synchronized long registerNewMapping(String str) {
		long value = counter++;
		Long old = strToLong.putIfAbsent(str, value);
		if(old == null) {
			// we did the put
			longToStr.put(value, str);
		}
		charCount.inc(str.length());
		return value;
	}
}
