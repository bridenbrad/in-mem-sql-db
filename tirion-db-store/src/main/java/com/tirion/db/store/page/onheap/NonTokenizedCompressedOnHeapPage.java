/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.onheap;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.extractor.ArrayExtractor;
import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.array.Array;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NonTokenizedCompressedOnHeapPage extends OnHeapPage {

	public NonTokenizedCompressedOnHeapPage(Header header, Column owner, Runtime runtime, Array array, DataStats stats) {
		super(header, owner, runtime, array, stats);
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.ON_HEAP_NON_TOKENIZED_COMPRESSED;
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
	public final List<Object> getValues(List<Long> rowIds) {
		ArrayExtractor arrayExtractor = getOwner().getArrayExtractor();
		Object array = getUncompressedArray();
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for(Long rowId : rowIds) {
			assertValidRowId(rowId);
			if(hasNullBitmap() && isNull(rowId)) {
				result.add(null);
			} else {				
				result.add(arrayExtractor.extract(getArrayIndex(rowId), array));
			}
		}
		return result;
	}

	@Override
	public long sizeInBytes() {
//		return super.getAbstractSize() + 8 + compressedArray.length;
		throw new NotYetImplementedException();
	}

	private Object getUncompressedArray() {
		return getCompressor().uncompress(getArray()).getUnderlying();
	}
}
