/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.offheap;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.copier.ByteCopier;
import com.tirion.common.copier.Copier;
import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.array.NativeArray;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TokenizedCompressedOffHeapPage extends OffHeapPage {

	private final Copier copier;
	
	public TokenizedCompressedOffHeapPage(Header header, Column owner, Runtime runtime, Buffer buffer, DataStats stats) {
		super(header, owner, runtime, buffer, stats);
		copier = new ByteCopier();		
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.OFF_HEAP_TOKENIZED_COMPRESSED;
	}

	@Override
	public Object getUnderlying() {
		return getUncompressedArray();
	}

	@Override
	public Object getUnderlying(boolean detokenize) {
		Object array = getUncompressedArray();
		if(!detokenize) {
			return array;
		}
		return getTokenizer().detokenize(
				new NativeArray(new Spec(getType(), getSequence().getCount(), false, getNullBitmap()), array)).getUnderlying();
	}

	@Override
	public Object getValue(long rowId) {
		assertValidRowId(rowId);
		if(hasNullBitmap() && isNull(rowId)) {
			return null;
		}
		Object array = getUncompressedArray();
		return getOwner().getTokenMap().getValueFor(Array.get(array, getArrayIndex(rowId)), true);
	}
	
	@Override
	public List<Object> getValues(List<Long> rowIds) {
		Object array = getUncompressedArray();
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for(Long rowId : rowIds) {
			assertValidRowId(rowId);
			if(hasNullBitmap() && isNull(rowId)) {
				result.add(null);
			} else {
				Object token = Array.get(array, getArrayIndex(rowId));
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
	
	private Object getUncompressedArray() {
		Buffer buffer = getBuffer();
		byte[] data = (byte[]) copier.toArray((ByteBuffer)buffer.getUnderlying(), buffer.getPosition(), buffer.size());
		return getCompressor().uncompress(data, getOwner().getTokenType());
	}
}
