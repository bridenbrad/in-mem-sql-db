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
public final class NestedLoopOperatorStatistics implements Statistics {

	@JsonProperty
	private final long consumedFromRight;
	@JsonProperty
	private final long consumedFromLeft;
	@JsonProperty
	private final long produced;
	
	public NestedLoopOperatorStatistics(long consumedFromRight,
			long consumedFromLeft, long produced) {
		super();
		this.consumedFromRight = consumedFromRight;
		this.consumedFromLeft = consumedFromLeft;
		this.produced = produced;
	}

	@Override
	public Kind getKind() {
		return Kind.NESTED_LOOP_OPERATOR;
	}

	/**
	 * Number of tuples consumed from right operator.
	 */
	public long getConsumedFromRight() {
		return consumedFromRight;
	}

	/**
	 * Number of tuples consumed from left operator.
	 */
	public long getConsumedFromLeft() {
		return consumedFromLeft;
	}

	/**
	 * Number of tuples this operator produced.
	 */
	public long getProduced() {
		return produced;
	}

	@Override
	public String toString() {
		return "NestedLoopOperatorStatistics [consumedFromRight="
				+ consumedFromRight + ", consumedFromLeft=" + consumedFromLeft
				+ ", produced=" + produced + "]";
	}
}
