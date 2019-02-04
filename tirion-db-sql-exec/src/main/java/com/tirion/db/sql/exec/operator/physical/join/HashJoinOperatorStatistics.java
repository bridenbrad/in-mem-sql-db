/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.join;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class HashJoinOperatorStatistics implements Statistics {

	@JsonProperty
	private final long consumedFromRight;
	@JsonProperty
	private final long keyCount;
	@JsonProperty
	private final long mapBuildDuration;
	@JsonProperty
	private final long consumedFromLeft;
	@JsonProperty
	private final long produced;

	public HashJoinOperatorStatistics(long consumedFromRight, long keyCount,
			long mapBuildDuration, long consumedFromLeft, long produced) {
		super();
		this.consumedFromRight = consumedFromRight;
		this.keyCount = keyCount;
		this.mapBuildDuration = mapBuildDuration;
		this.consumedFromLeft = consumedFromLeft;
		this.produced = produced;
	}
	
	@Override
	public Kind getKind() {
		return Kind.HASH_JOIN_OPERATOR;
	}

	/**
	 * Number of tuples consumed from right.
	 */
	public long getConsumedFromRight() {
		return consumedFromRight;
	}
	
	/**
	 * Number of keys in the map.
	 */
	public long getKeyCount() {
		return keyCount;
	}
	
	/**
	 * How long it took to build map.
	 */
	public long getMapBuildDuration() {
		return mapBuildDuration;
	}
	
	/**
	 * Number of tuples consumed from left.
	 */
	public long getConsumedFromLeft() {
		return consumedFromLeft;
	}
	
	/**
	 * Number of tuples produced by operator.
	 */
	public long getProduced() {
		return produced;
	}

	@Override
	public String toString() {
		return "HashJoinOperatorStatistics [consumedFromRight="
				+ consumedFromRight + ", keyCount=" + keyCount
				+ ", mapBuildDuration=" + mapBuildDuration
				+ ", consumedFromLeft=" + consumedFromLeft + ", produced="
				+ produced + "]";
	}
}
