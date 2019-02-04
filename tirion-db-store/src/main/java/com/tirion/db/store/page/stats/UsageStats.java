/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.stats;

/**
 * Stats about page usage.
 */
public interface UsageStats extends UsageStatsAware {
	
	long getSkipMinMax();
	
	long getSkipRange();
	
	long getDecompress();
	
	long getDetokenize();
	
	long getProject();
	
	long getProjectMultipleCount();
	
	long getProjectMultipleSum();
}
