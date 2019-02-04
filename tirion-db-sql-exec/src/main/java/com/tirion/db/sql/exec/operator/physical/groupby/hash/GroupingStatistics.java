/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GroupingStatistics implements Statistics {

	@JsonProperty
	private final long consumedTuples;
	@JsonProperty
	private final long keyCount;
	@JsonProperty
	private final long flushDuration;
	
	public GroupingStatistics(long consumedTuples, long keyCount, long flushDuration) {
		super();
		this.consumedTuples = consumedTuples;
		this.keyCount = keyCount;
		this.flushDuration = flushDuration;
	}

	@Override
	public Kind getKind() {
		return Kind.GROUP_OPERATOR;
	}



	public long getConsumedTuples() {
		return consumedTuples;
	}

	public long getKeyCount() {
		return keyCount;
	}

	public long getFlushDuration() {
		return flushDuration;
	}
}
