/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.analyzer.range;

public abstract class AbstractRangeAnalyzer implements RangeAnalyzer {
	
	private final RangesConf conf;

	public AbstractRangeAnalyzer(RangesConf conf) {
		this.conf = conf;
	}

	protected final int getMaxSkipRangesPerPage() {
		return conf.getMaxRangesPerPage();
	}

	protected final int getSkipRangeDiscreteThreshold() {
		return conf.getRangeDiscreteThreshold();
	}

	protected final int getSkipRangeRealThreshold() {
		return conf.getRangeRealThreshold();
	}
}
