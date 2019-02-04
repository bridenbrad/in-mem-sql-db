/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats;

import com.tirion.common.type.Type;
import com.tirion.db.store.builder.stats.analyzer.range.RangesConf;

public interface AnalyzerFactory {

	/**
	 * Returns null in case both min max & ranges are off.
	 */
	Analyzer newAnalyzer(Type type, boolean mmOn, boolean rangeOn, RangesConf rangesConf);
}
