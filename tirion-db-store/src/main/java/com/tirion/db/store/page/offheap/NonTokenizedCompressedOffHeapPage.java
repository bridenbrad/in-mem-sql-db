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
import com.tirion.common.copier.ByteCopier;
import com.tirion.common.copier.Copier;
import com.tirion.common.extractor.ArrayExtractor;
import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NonTokenizedCompressedOffHeapPage extends OffHeapPage {

	private final Copier copier;
	
	public NonTokenizedCompressedOffHeapPage(Header header, Column owner, Runtime runtime, Buffer buffer, DataStats stats) {
		super(header, owner, runtime, buffer, stats);
		copier = new ByteCopier();
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.OFF_HEAP_NON_TOKENIZED_COMPRESSED;
	}

	@Override
	public Object getUnderlying() {
		return getUncompressedArray();
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
		Object array = getUncompressedArray();
		return getOwner().getArrayExtractor().extract(getArrayIndex(rowId), array);
	}
	
	@Override
	public List<Object> getValues(List<Long> rowIds) {
		ArrayExtractor arrayExtractor = getOwner().getArrayExtractor();
		Object array = getUncompressedArray();
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for(Long rowId : rowIds) {
			if(hasNullBitmap() && isNull(rowId)) {
				result.add(null);
			} else {				
				result.add(arrayExtractor.extract(getArrayIndex(rowId), array));
			}
		}
		return result;
	}
	
	private Object getUncompressedArray() {
		Buffer buffer = getBuffer();
		byte[] data = (byte[]) copier.toArray((ByteBuffer)buffer.getUnderlying(), buffer.getPosition(), buffer.size()); 
		return getCompressor().uncompress(data, getType());
	}
	
//	private int getBufferIndex(long rowId) {
//		return ((int)(rowId - getStartRowId())) * getType().getWidth() + buffer.getPosition();
//	}
	
	@Override
	public long sizeInBytes() {
//		return super.getAbstractSize() + 16 + buffer.sizeInBytes();
		throw new NotYetImplementedException();
	}
}
