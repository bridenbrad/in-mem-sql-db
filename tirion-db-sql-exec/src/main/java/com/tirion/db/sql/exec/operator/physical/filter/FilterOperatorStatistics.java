/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.filter;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FilterOperatorStatistics implements Statistics {

	@JsonProperty
	private final long consumedFromSource;
	@JsonProperty
	private final long produced;
	@JsonProperty
	private final long filtered;

	public FilterOperatorStatistics(long consumedFromSource, long produced) {
		super();
		this.consumedFromSource = consumedFromSource;
		this.produced = produced;
		this.filtered = consumedFromSource - produced;
	}
	
	@Override
	public Kind getKind() {
		return Kind.FILTER_OPERATOR;
	}

	/**
	 * Number of tuples that filter dropped.
	 */
	public long getFiltered() {
		return filtered;
	}

	/**
	 * Number of consumed tuples.
	 */
	public long getConsumedFromSource() {
		return consumedFromSource;
	}

	/**
	 * Number of produced tuples.
	 */
	public long getProduced() {
		return produced;
	}

	@Override
	public String toString() {
		return "FilterOperatorStatistics [consumedFromSource="
				+ consumedFromSource + ", produced=" + produced + ", filtered="
				+ filtered + "]";
	}
}
