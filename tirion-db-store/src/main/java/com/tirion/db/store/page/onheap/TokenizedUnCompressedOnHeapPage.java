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
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TokenizedUnCompressedOnHeapPage extends OnHeapPage {
	
	public TokenizedUnCompressedOnHeapPage(Header header, Column owner, Runtime runtime, com.tirion.common.sequence.array.Array array, DataStats stats) {
		super(header, owner, runtime, array, stats);
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.ON_HEAP_TOKENIZED_UNCOMPRESSED;
	}

	@Override
	public Object getUnderlying() {
		return getJavaArray();
	}

	@Override
	public Object getUnderlying(boolean detokenize) {
		if(detokenize) {
			return getTokenizer().detokenize(getArray()).getUnderlying();
		}
		return getJavaArray();
	}

	@Override
	public Object getValue(long rowId) {
		assertValidRowId(rowId);
		if(hasNullBitmap() && isNull(rowId)) {
			return null;
		}
		Object token = getOwner().getArrayExtractor().extract(getArrayIndex(rowId), getJavaArray());
		return getOwner().getTokenMap().getValueFor(token, true);
	}
	
	@Override
	public List<Object> getValues(List<Long> rowIds) {
		ArrayExtractor arrayExtractor = getOwner().getArrayExtractor();
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for(Long rowId : rowIds) {
			assertValidRowId(rowId);
			if(hasNullBitmap() && isNull(rowId)) {
				result.add(null);
			} else {				
//				Object token = java.lang.reflect.Array.get(array, getArrayIndex(rowId));
				Object token = arrayExtractor.extract(getArrayIndex(rowId), getJavaArray());
				result.add(getOwner().getTokenMap().getValueFor(token, true));
			}
		}
		return result;
	}

	@Override
	public long sizeInBytes() {
//		return super.getAbstractSize() + 8 + Array.getLength(array) * getOwner().getTokenType().getWidth();
		throw new NotYetImplementedException();
	}
}
