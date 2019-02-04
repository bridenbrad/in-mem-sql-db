/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats;

import com.tirion.common.type.Type;
import com.tirion.db.store.builder.stats.analyzer.minmax.ByteMinMaxAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.minmax.DoubleMinMaxAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.minmax.FloatMinMaxAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.minmax.IntegerMinMaxAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.minmax.LongMinMaxAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.minmax.ShortMinMaxAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.range.ByteRangeAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.range.DoubleRangeAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.range.FloatRangeAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.range.IntegerRangeAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.range.LongRangeAnalyzer;
import com.tirion.db.store.builder.stats.analyzer.range.RangesConf;
import com.tirion.db.store.builder.stats.analyzer.range.ShortRangeAnalyzer;

public final class SimpleAnalyzerFactory implements AnalyzerFactory {

	@Override
	public Analyzer newAnalyzer(Type type, boolean mmOn, boolean rangeOn, RangesConf rangesConf) {
		if(!mmOn && !rangeOn) {
			return null;
		}
		switch (type) {
			case BYTE:
				return new SimpleAnalyzer(mmOn ? new ByteMinMaxAnalyzer() : null, 
										  rangeOn ? new ByteRangeAnalyzer(rangesConf) : null);
			case SHORT:
				return new SimpleAnalyzer(mmOn ? new ShortMinMaxAnalyzer() : null, 
						  				  rangeOn ? new ShortRangeAnalyzer(rangesConf) : null);
			case INT:
				return new SimpleAnalyzer(mmOn ? new IntegerMinMaxAnalyzer() : null, 
		  				  				  rangeOn ? new IntegerRangeAnalyzer(rangesConf) : null);
			case LONG:
				return new SimpleAnalyzer(mmOn ? new LongMinMaxAnalyzer() : null, 
		  				  				  rangeOn ? new LongRangeAnalyzer(rangesConf) : null);
			case FLOAT:
				return new SimpleAnalyzer(mmOn ? new FloatMinMaxAnalyzer() : null, 
		  				  				  rangeOn ? new FloatRangeAnalyzer(rangesConf) : null);
			case DOUBLE:
				return new SimpleAnalyzer(mmOn ? new DoubleMinMaxAnalyzer() : null, 
		  				  				  rangeOn ? new DoubleRangeAnalyzer(rangesConf) : null);
			default:
				throw new IllegalArgumentException("Unexpected type '" + type + "'");
		}
	}

}
