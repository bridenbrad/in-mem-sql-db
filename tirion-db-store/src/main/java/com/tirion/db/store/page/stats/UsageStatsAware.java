/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.stats;

import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.builder.stats.model.minmax.MinMax;

public interface UsageStatsAware {

	/**
	 * Page skipped due to {@link DataStats}'s {@link MinMax}.
	 */
	void onPageSkipMinMax();
	
	void onPageSkipRanges();
	
	void onPageDecompress();
	
	void onPageDetokenize();
	
	/**
	 * When single-row project is called.
	 */
	void onProject();
	
	/**
	 * When multi row ID project is called.
	 */
	void onProject(int rowCount);
}
