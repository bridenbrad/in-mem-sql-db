/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.tirion.common.NotYetImplementedException;
import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.runtime.Runtime;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.column.Column;
import com.tirion.db.store.page.header.Header;
import com.tirion.db.store.page.stats.UsageStats;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BooleanPage extends AbstractPage {

	private final Bitmap bitmap;
	private final Bitmap nullBitmap;
	
	public BooleanPage(Header header, Column owner, Runtime runtime, Bitmap bitmap, Bitmap nullBitmap) {
		super(header, owner, runtime);
		this.bitmap = bitmap;
		this.nullBitmap = nullBitmap;
	}
	
	@Override
	public UsageStats getUsageStats() {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean hasNullBitmap() {
		return nullBitmap != null;
	}

	@Override
	public Bitmap getNullBitmap() {
		return nullBitmap;
	}

	@Override
	public Kind getKind() {
		return Kind.BOOLEAN;
	}

	@Override
	public boolean hasDataStats() {
		return false;
	}

	@Override
	public DataStats getDataStats() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getUnderlying() {
		return bitmap;
	}
	
	@Override
	public Object getUnderlying(boolean detokenize) {
		return getUnderlying();
	}

	@Override
	public Object getValue(long rowId) {
		assertValidRowId(rowId);
		EWAHCompressedBitmap bm = new EWAHCompressedBitmap();
		bm.set(getIndex(rowId));
		EWAHCompressedBitmap underlying = bitmap.getUnderlying();
		return underlying.and(bm).intIterator().hasNext();
	}

	@Override
	public List<Object> getValues(List<Long> rowIds) { // TODO more efficient implementation
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for(Long rowId : rowIds) {
			result.add(getValue(rowId));
		}
//		EWAHCompressedBitmap bm = new EWAHCompressedBitmap();
//		for(Long rowId : rowIds) {
//			assertValidRowId(rowId);
//			bm.set(getIndex(rowId));
//		}
//		EWAHCompressedBitmap underlying = bitmap.getUnderlying();
//		List<Integer> truePositions = underlying.and(bm).getPositions();
//		return buildResult(rowIds, truePositions);
		return result;
	}

	@Override
	public long sizeInBytes() {
		return super.getAbstractSize() + 8 + bitmap.sizeInBytes();
	}
	
	private int getIndex(long rowId) {
		return (int)(rowId - getStartRowId());
	}
	
	/**
	 * 
	 * @param rowIds
	 * @param truePositions somewhere between 0 until max number of rows within page
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<Object> buildResult(List<Long> rowIds, List<Integer> truePositions) {
		// assume all are false
		List<Object> result = new ArrayList<Object>(rowIds.size());
		for (int i = 0; i < rowIds.size(); i++) {
			result.add(false);
		}
		
		// set to true if true
		Set<Long> set = new HashSet<Long>(rowIds);
		for(Integer truePosition : truePositions) {
			if(set.contains(truePosition + getStartRowId())) {
				result.set(truePosition, true);
			}
		}
		return result;
	}
}
