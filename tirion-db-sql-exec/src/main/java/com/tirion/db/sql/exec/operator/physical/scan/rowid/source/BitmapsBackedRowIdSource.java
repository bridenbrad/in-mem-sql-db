/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.rowid.source;

import java.util.LinkedList;
import java.util.List;

import com.googlecode.javaewah.IntIterator;
import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.common.bitmap.CompressedBitmap;
import com.tirion.db.common.Constants;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BitmapsBackedRowIdSource implements RowIdSource {

	private final Bitmaps bitmaps;
	
	private int index = 0;
	private IntIterator iterator;
	
	public BitmapsBackedRowIdSource(Bitmaps bitmaps) {
		super();
		this.bitmaps = bitmaps;
		iterator = getEwahIterator();
	}

	@Override
	public boolean hasNext() {
		return iterator != null && iterator.hasNext();
	}

	@Override
	public long next() {
		final long rowId = Constants.ROWS_PER_PAGE * index + iterator.next();
		if(!iterator.hasNext()) {
			iterator = getEwahIterator();
		}
		return rowId;
	}
	
	@Override
	public boolean isEmpty() {
		return iterator == null;
	}

	@Override
	public List<Long> asList() {
		List<Long> list = new LinkedList<Long>();
		BitmapsBackedRowIdSource source = new BitmapsBackedRowIdSource(bitmaps);
		while(source.hasNext()) {
			list.add(source.next());
		}
		return list;
	}

	private IntIterator getEwahIterator() {
		while(index < bitmaps.getCount()) {
			Bitmap bitmap = bitmaps.get(index++);
			if(bitmap != null) {
				CompressedBitmap cBitmap = (CompressedBitmap) bitmap;
				IntIterator iterator = cBitmap.getUnderlying().intIterator();
				if(iterator.hasNext()) {
					return iterator;
				}
			} 
		}
		return null;
	}
}
