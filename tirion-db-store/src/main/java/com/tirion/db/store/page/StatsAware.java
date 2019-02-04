/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page;

import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.page.stats.UsageStats;

public interface StatsAware {

	DataStats getDataStats();
	
	UsageStats getUsageStats();
	
	boolean hasDataStats();
}
