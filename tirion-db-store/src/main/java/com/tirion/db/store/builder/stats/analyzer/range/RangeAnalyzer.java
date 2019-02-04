/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.analyzer.range;

import com.tirion.common.sequence.array.Array;
import com.tirion.db.store.builder.stats.model.range.Ranges;

public interface RangeAnalyzer {

	Ranges analyze(Array array);
}
