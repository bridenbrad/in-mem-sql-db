/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model.options;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.Constants;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Options implements Serializable {

	private static final long serialVersionUID = -1214877538273201801L;
	
	public static final Options DEFAULT = new Options();

	@JsonProperty
	private final boolean nullable;
	@JsonProperty
	private final boolean tokenize;
	@JsonProperty
	private final boolean bmIndex;
	@JsonProperty
	private final boolean mmStats;
	@JsonProperty
	private final boolean rangeStats = false; // TODO disabled for now
	@JsonProperty
	private final boolean compress;
	@JsonProperty
	private final boolean offHeap;
	@JsonProperty
	private final long distinct;
	
	/**
	 * All set to false.
	 */
	public Options() {
		this(true, false, false, false, false, false, false, Long.MAX_VALUE);
	}
	
	public Options(boolean nullable, boolean tokenize, boolean bmIndex, boolean mmStats, boolean rangeStats, boolean compress, boolean offHeap, long distinct) {
		this.nullable = nullable;
		this.tokenize = tokenize;
		this.bmIndex = bmIndex;
		this.mmStats = mmStats;
//		this.rangeStats = rangeStats;
		this.compress = compress;
		this.offHeap = offHeap;
		this.distinct = distinct;
		
		// sanity checks
		if(bmIndex && distinct > com.tirion.db.common.Constants.BM_INDEX_MAX_KEY_COUNT) {
			throw new IllegalStateException("Can not create bitmap index if number of distinct values is larger than " + com.tirion.db.common.Constants.BM_INDEX_MAX_KEY_COUNT);
		}
		if(tokenize && distinct > Constants.SHORT_DISTINCT_COUNT) {
			throw new IllegalStateException("Can not tokenize if number of distinct values is larger than " + Constants.SHORT_DISTINCT_COUNT);	
		}
		if(bmIndex && (mmStats || rangeStats)) {
			throw new IllegalStateException("Column can not have both bitmap index & stats");
		}
	}
	
	public boolean isNullable() {
		return nullable;
	}

	public long getDistinct() {
		return distinct;
	}

	public boolean isTokenized() {
		return tokenize;
	}

	public boolean hasBmIndex() {
		return bmIndex;
	}

	public boolean hasMmStats() {
		return mmStats;
	}
	
	public boolean hasRangeStats() {
		return rangeStats;
	}

	public boolean isCompressed() {
		return compress;
	}

	public boolean isOffHeap() {
		return offHeap;
	}

	@Override
	public String toString() {
		return "Options [nullable=" + nullable + ", tokenize=" + tokenize
				+ ", bmIndex=" + bmIndex + ", mmStats=" + mmStats
				+ ", rangeStats=" + rangeStats + ", compress=" + compress
				+ ", offHeap=" + offHeap + ", distinct=" + distinct + "]";
	}
	
	public static Options buildFrom(boolean nullable, String value) {
		Map<Kind, String> map = new HashMap<Kind, String>();
		StringTokenizer tokenizer = new StringTokenizer(value, ":");
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if(token.isEmpty()) {
				continue;
			}
			String[] parts = token.split("=");
			for (int i = 0; i < parts.length; i++) {
				parts[i] = parts[i].trim();
				if(parts[i].isEmpty()) {
					throw new IllegalArgumentException(value);
				}
			}
			map.put(Kind.parseFrom(parts[0]), parts[1]);
		}
		boolean tokenize = false;
		boolean index = false;
		boolean mmStats = false;
		boolean rangeStats = false;
		boolean compress = true;
		boolean offHeap = false;
		long distinct = Long.MAX_VALUE;
		
		if(map.containsKey(Kind.TOKENIZE)) {
			tokenize = Boolean.parseBoolean(map.get(Kind.TOKENIZE));
			map.remove(Kind.TOKENIZE);
		}
		if(map.containsKey(Kind.BM_INDEX)) {
			index = Boolean.parseBoolean(map.get(Kind.BM_INDEX));
			map.remove(Kind.BM_INDEX);
		}
		if(map.containsKey(Kind.MM_STATS)) {
			mmStats = Boolean.parseBoolean(map.get(Kind.MM_STATS));
			map.remove(Kind.MM_STATS);
		}
		if(map.containsKey(Kind.RANGE_STATS)) {
			rangeStats = Boolean.parseBoolean(map.get(Kind.RANGE_STATS));
			map.remove(Kind.RANGE_STATS);
		}
		if(map.containsKey(Kind.COMPRESS)) {
			compress = Boolean.parseBoolean(map.get(Kind.COMPRESS));
			map.remove(Kind.COMPRESS);
		}
		if(map.containsKey(Kind.OFFHEAP)) {
			offHeap = Boolean.parseBoolean(map.get(Kind.OFFHEAP));
			map.remove(Kind.OFFHEAP);
		}
		if(map.containsKey(Kind.DISTINCT)) {
			distinct = Long.parseLong(map.get(Kind.DISTINCT));
			map.remove(Kind.DISTINCT);
		}
		if(!map.isEmpty()) {
			throw new IllegalArgumentException("Unknown option '" + map.toString() + "'");
		}
		return new Options(nullable, tokenize, index, mmStats, rangeStats, compress, offHeap, distinct);
	}
}
