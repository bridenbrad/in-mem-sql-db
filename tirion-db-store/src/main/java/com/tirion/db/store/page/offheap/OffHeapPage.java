/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.offheap;

import java.nio.ByteBuffer;

import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.StatsAwarePage;
import com.tirion.db.store.page.header.Header;

/**
 * Underlying {@link ByteBuffer} will be exposed only in
 * case page is uncompressed.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class OffHeapPage extends StatsAwarePage {

	public OffHeapPage(Header header, Column owner, Runtime runtime, Buffer buffer, DataStats stats) {
		super(header, owner, runtime, buffer, stats);
	}
	
	public final int getPosition() {
		return getBuffer().getPosition();
	}
	
	protected final Buffer getBuffer() {
		return (Buffer)getSequence();
	}
	
	protected final ByteBuffer getByteBuffer() {
		return (ByteBuffer) getBuffer().getUnderlying();
	}
}
