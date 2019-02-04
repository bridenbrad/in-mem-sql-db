/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.stats;

public final class NoopUsageStats implements UsageStats {
	
	@Override
	public long getSkipMinMax() {
		return -1;
	}

	@Override
	public long getSkipRange() {
		return -1;
	}

	@Override
	public long getDecompress() {
		return -1;
	}

	@Override
	public long getDetokenize() {
		return -1;
	}

	@Override
	public long getProject() {
		return -1;
	}

	@Override
	public long getProjectMultipleCount() {
		return -1;
	}

	@Override
	public long getProjectMultipleSum() {
		return -1;
	}

	@Override
	public void onPageSkipMinMax() {
	}

	@Override
	public void onPageSkipRanges() {
	}

	@Override
	public void onPageDecompress() {
	}

	@Override
	public void onPageDetokenize() {
	}

	@Override
	public void onProject() {
	}

	@Override
	public void onProject(int rowCount) {
	}
}
