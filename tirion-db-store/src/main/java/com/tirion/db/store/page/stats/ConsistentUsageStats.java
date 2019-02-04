/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.stats;

import java.util.concurrent.atomic.AtomicLong;

public final class ConsistentUsageStats implements UsageStats {

	private final AtomicLong skipMinMax = new AtomicLong();
	private final AtomicLong skipRange = new AtomicLong();
	private final AtomicLong decompress = new AtomicLong();
	private final AtomicLong detokenize = new AtomicLong();
	private final AtomicLong project = new AtomicLong();
	private final AtomicLong projectMultipleCount = new AtomicLong();
	private final AtomicLong projectMultipleSum = new AtomicLong();
	
	@Override
	public long getSkipMinMax() {
		return skipMinMax.get();
	}

	@Override
	public long getSkipRange() {
		return skipRange.get();
	}

	@Override
	public long getDecompress() {
		return decompress.get();
	}

	@Override
	public long getDetokenize() {
		return detokenize.get();
	}

	@Override
	public long getProject() {
		return project.get();
	}

	@Override
	public long getProjectMultipleCount() {
		return projectMultipleCount.get();
	}

	@Override
	public long getProjectMultipleSum() {
		return projectMultipleSum.get();
	}

	@Override
	public void onPageSkipMinMax() {
		skipMinMax.incrementAndGet();
	}

	@Override
	public void onPageSkipRanges() {
		skipRange.incrementAndGet();
	}

	@Override
	public void onPageDecompress() {
		decompress.incrementAndGet();
	}

	@Override
	public void onPageDetokenize() {
		detokenize.incrementAndGet();
	}

	@Override
	public void onProject() {
		project.incrementAndGet();
	}

	@Override
	public void onProject(int rowCount) {
		projectMultipleCount.incrementAndGet();
		projectMultipleSum.addAndGet(rowCount);
	}
}
