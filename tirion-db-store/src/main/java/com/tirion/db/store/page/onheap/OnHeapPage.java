/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.onheap;

import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.array.Array;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.StatsAwarePage;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class OnHeapPage extends StatsAwarePage {
	
	public OnHeapPage(Header header, Column owner, Runtime runtime, Array array, DataStats stats) {
		super(header, owner, runtime, array, stats);
	}
	
	/**
	 * Returns Java array.
	 */
	protected final Object getJavaArray() {
		return getSequence().getUnderlying();
	}
	
	/**
	 * Returns {@link Array}.
	 */
	protected final Array getArray() {
		return (Array)getSequence();
	}
}
