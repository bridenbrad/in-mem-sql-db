/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.offheap;

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
public final class TokenizedUnCompressedOffHeapPage extends OffHeapPage {
	
	private final BufferExtractor bufferExtractor;

	public TokenizedUnCompressedOffHeapPage(Header header, Column owner, Runtime runtime, Buffer buffer, DataStats stats) {
		super(header, owner, runtime, buffer, stats);		
		this.bufferExtractor = ExtractorFactory.newBufferExtractor(getOwner().getTokenType(), getByteBuffer());
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.OFF_HEAP_TOKENIZED_UNCOMPRESSED;
	}
	
	@Override
	public Object getUnderlying() {
		return getByteBuffer();
	}

	@Override
	public Object getUnderlying(boolean detokenize) {
		if(!detokenize) {
			return getUnderlying();
		}
		return getTokenizer().detokenize(getBuffer()).getUnderlying();
	}

	@Override
	public Object getValue(long rowId) {
		assertValidRowId(rowId);
		if(hasNullBitmap() && isNull(rowId)) {
			return null;
		}
		return getOwner().getTokenMap().getValueFor(bufferExtractor.extract(getBufferIndex(rowId)), true);
	}
	
	@Override
	public List<Object> getValues(List<Long> rowIds) {
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for(Long rowId : rowIds) {
			assertValidRowId(rowId);
			if(hasNullBitmap() && isNull(rowId)) {
				result.add(null);
			} else {				
				Object token = bufferExtractor.extract(getBufferIndex(rowId));
				result.add(getOwner().getTokenMap().getValueFor(token, true));
			}
		}
		return result;
	}

	@Override
	public long sizeInBytes() {
//		return super.getAbstractSize() + 16 + buffer.sizeInBytes();
		throw new NotYetImplementedException();
	}
	
	private int getBufferIndex(long rowId) {
		return ((int)(rowId - getStartRowId())) * getOwner().getTokenType().getWidth() + getBuffer().getPosition();
	}
}
