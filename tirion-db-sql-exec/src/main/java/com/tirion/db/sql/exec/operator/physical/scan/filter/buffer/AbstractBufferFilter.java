/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.filter.buffer;

import java.nio.ByteBuffer;

import com.tirion.db.sql.exec.operator.physical.scan.rowid.sink.RowIdSink;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractBufferFilter implements BufferFilter {

	protected final RowIdSink rowIdSink;
	protected final long startRowId;
	protected final int startPosition;
	protected final int count;
	protected final ByteBuffer buffer;
	
	public AbstractBufferFilter(RowIdSink rowIdSink, long startRowId,
			int startPosition, int count, ByteBuffer buffer) {
		super();
		this.rowIdSink = rowIdSink;
		this.startRowId = startRowId;
		this.startPosition = startPosition;
		this.count = count;
		this.buffer = buffer;
	}
}
