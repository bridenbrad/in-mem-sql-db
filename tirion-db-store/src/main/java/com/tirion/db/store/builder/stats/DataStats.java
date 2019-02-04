/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.SizeAware;
import com.tirion.db.store.builder.stats.model.minmax.MinMax;
import com.tirion.db.store.builder.stats.model.range.Ranges;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DataStats implements SizeAware {

	@JsonProperty
	private final MinMax minMax;
	@JsonProperty
	private final Ranges ranges;
	
	public DataStats(MinMax minMax, Ranges ranges) {
		this.minMax = minMax;
		this.ranges = ranges;
	}
	
	public boolean hasRanges() {
		return getRanges() != null;
	}

	public boolean hasMinMax() {
		return getMinMax() != null;
	}
	
	public MinMax getMinMax() {
		return minMax;
	}

	public Ranges getRanges() {
		return ranges;
	}
	
	@Override
	public long sizeInBytes() {
		return 4 + 8 + 8 + (minMax != null ? minMax.sizeInBytes() : 0) + (ranges != null ? ranges.sizeInBytes() : 0);
	}
}
