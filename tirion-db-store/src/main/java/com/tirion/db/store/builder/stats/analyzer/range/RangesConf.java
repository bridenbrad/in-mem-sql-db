/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.analyzer.range;

public final class RangesConf {

	public static final RangesConf DEFAULT = new RangesConf(2, 1024 * 1024, 1024 * 1024);

	private final int maxRangesPerPage;
	private final int rangeDiscreteThreshold;
	private final int rangeRealThreshold;
	
	public RangesConf(int maxRangesPerPage, int rangeDiscreteThreshold,
			int rangeRealThreshold) {
		super();
		this.maxRangesPerPage = maxRangesPerPage;
		this.rangeDiscreteThreshold = rangeDiscreteThreshold;
		this.rangeRealThreshold = rangeRealThreshold;
	}

	public int getMaxRangesPerPage() {
		return maxRangesPerPage;
	}

	public int getRangeDiscreteThreshold() {
		return rangeDiscreteThreshold;
	}

	public int getRangeRealThreshold() {
		return rangeRealThreshold;
	}
}
