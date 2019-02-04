/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.slice;

import com.tirion.common.sequence.Sequence;
import com.tirion.db.store.builder.stats.DataStats;

public final class StatsSequenceSlice extends SequenceSlice {

	private final DataStats[] stats;

	public StatsSequenceSlice(int slice, int rowCount, Sequence[] sequences, DataStats[] stats) {
		super(slice, rowCount, sequences);
		this.stats = stats;
	}

	public DataStats[] getStats() {
		return stats;
	}
}
