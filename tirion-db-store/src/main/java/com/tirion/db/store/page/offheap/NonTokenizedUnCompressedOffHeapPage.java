/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.offheap;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.extractor.BufferExtractor;
import com.tirion.common.extractor.ExtractorFactory;
import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NonTokenizedUnCompressedOffHeapPage extends OffHeapPage {

	private final BufferExtractor bufferExtractor;

	public NonTokenizedUnCompressedOffHeapPage(Header header, Column owner, Runtime runtime, Buffer buffer, DataStats stats) {
		super(header, owner, runtime, buffer, stats);
		bufferExtractor = ExtractorFactory.newBufferExtractor(getType(), (ByteBuffer) getBuffer().getUnderlying());
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.OFF_HEAP_NON_TOKENIZED_UNCOMPRESSED;
	}

	@Override
	public Object getUnderlying() {
		return getBuffer().getUnderlying();
	}

	@Override
	public Object getUnderlying(boolean detokenize) {
		return getUnderlying();
	}

	@Override
	public Object getValue(long rowId) {
		assertValidRowId(rowId);
		if(hasNullBitmap() && isNull(rowId)) {
			return null;
		}
		return bufferExtractor.extract(getBufferIndex(rowId));
	}
	
	@Override
	public List<Object> getValues(List<Long> rowIds) {
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for(Long rowId : rowIds) {
			result.add(getValue(rowId));
		}
		return result;
	}

	@Override
	public long sizeInBytes() {
//		return super.getAbstractSize() + 16 + buffer.sizeInBytes();
		throw new NotYetImplementedException();
	}
	
	private int getBufferIndex(long rowId) {
		return ((int)(rowId - getStartRowId())) * getType().getWidth() + getBuffer().getPosition();
	}
}
