/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.onheap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.runtime.Runtime;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NonTokenizedUnCompressedOnHeapPage extends OnHeapPage {
	
	public NonTokenizedUnCompressedOnHeapPage(Header header, Column owner, Runtime runtime, com.tirion.common.sequence.array.Array array, DataStats stats) {
		super(header, owner, runtime, array, stats);
	}
	
	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.ON_HEAP_NON_TOKENIZED_UNCOMPRESSED;
	}

	@Override
	public Object getUnderlying() {
		return getJavaArray();
	}

	@Override
	public Object getUnderlying(boolean detokenize) {
		return getJavaArray();
	}

	@Override
	public Object getValue(long rowId) {
		assertValidRowId(rowId);
		if(hasNullBitmap() && isNull(rowId)) {
			return null;
		}
		return Array.get(getJavaArray(), getArrayIndex(rowId));
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
//		return super.getAbstractSize() + 8 + Array.getLength(array) * getType().getWidth();
		throw new NotYetImplementedException();
	}
}
