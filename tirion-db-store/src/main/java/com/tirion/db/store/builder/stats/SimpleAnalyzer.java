/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats;

import com.tirion.common.sequence.array.Array;
import com.tirion.db.store.builder.stats.analyzer.minmax.MinMaxAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.range.RangeAnalyzer;
import com.tirion.db.store.builder.stats.model.minmax.MinMax;
import com.tirion.db.store.builder.stats.model.range.Ranges;

public final class SimpleAnalyzer implements Analyzer {

	private final MinMaxAnalyzer minMaxAnalyzer;
	private final RangeAnalyzer rangeAnalyzer;
	
	public SimpleAnalyzer(MinMaxAnalyzer minMaxAnalyzer, RangeAnalyzer rangeAnalyzer) {
		super();
		this.minMaxAnalyzer = minMaxAnalyzer;
		this.rangeAnalyzer = rangeAnalyzer;
	}

	@Override
	public DataStats analyze(Array array) {
		MinMax mm = null;
		if(minMaxAnalyzer != null) {
			mm = minMaxAnalyzer.analyze(array);
		}
		Ranges ranges = null;
		if(rangeAnalyzer != null) {
			ranges = rangeAnalyzer.analyze(array);
		}
		if(mm == null && ranges == null) {
			return null;
		}
		return new DataStats(mm, ranges);
	}	
}
