/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.Sequence;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;
import com.tirion.db.store.page.stats.NoopUsageStats;
import com.tirion.db.store.page.stats.UsageStats;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class StatsAwarePage extends SequencePage {

	@JsonProperty
	private final DataStats dataStats;
	
	// not final so that it can be disabled at runtime
	@JsonProperty
	private UsageStats usageStats = new NoopUsageStats(); 
	
	public StatsAwarePage(Header header, Column owner, Runtime runtime, Sequence sequence, DataStats dataStats) {
		super(header, owner, runtime, sequence);
		this.dataStats = dataStats;
	}
	
	public final void setUsageStats(UsageStats usageStats) {
		this.usageStats = usageStats;
	}

	@Override
	public final UsageStats getUsageStats() {
		return usageStats;
	}

	@Override
	public final DataStats getDataStats() {
		return dataStats;
	}

	@Override
	public final boolean hasDataStats() {
		return getDataStats() != null;
	}

	@Override
	protected final long getAbstractSize() {
		return super.getAbstractSize() + 8 + (dataStats != null ? dataStats.sizeInBytes() : 0);
	}
}
