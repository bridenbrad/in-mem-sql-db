/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.indexer;

import java.util.Map;

import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.common.bitmap.CompressedBitmap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractBmIndexer<T> implements BmIndexer<T> {

	private int maxSlice = -1;
	private final Map<T, Bitmaps> map;

	public AbstractBmIndexer(Map<T, Bitmaps> map) {
		this.map = map;
	}
	
	public final Map<T, Bitmaps> getMap() {
		return map;
	}

	protected synchronized final void registerNewValue(T value, int slice, int index) {
		if(slice > maxSlice) {
			expandCurrentBitmaps(slice);
		}
		Bitmaps bitmaps = map.get(value);
		if(bitmaps == null) {
			bitmaps = new Bitmaps(maxSlice + 1);
			nullFill(bitmaps, 0, maxSlice);
			map.put(value, bitmaps);
		}
		Bitmap bitmap = bitmaps.get(slice);
		if(bitmap == null) {
			bitmap = new CompressedBitmap();
			bitmaps.set(slice, bitmap);
		}
		bitmap.set(index);
	}
	
	private void expandCurrentBitmaps(int slice) {
		for(Bitmaps bitmaps : map.values()) {
			nullFill(bitmaps, maxSlice + 1, slice);
		}
		maxSlice = slice;		
	}
	
	/**
	 * Both inclusive.
	 */
	private void nullFill(Bitmaps bitmaps, int start, int end) {
		for (int i = start; i <= end; i++) {
			bitmaps.append((Bitmap)null);
		}
	}
}
