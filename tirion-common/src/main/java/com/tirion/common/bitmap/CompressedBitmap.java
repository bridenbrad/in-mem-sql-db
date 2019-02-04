/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.bitmap;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.tirion.common.Constants;
import com.tirion.common.NotYetImplementedException;
import com.tirion.common.ewah.EwahUtil;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CompressedBitmap implements Bitmap {

	private final EWAHCompressedBitmap underlying;

	/**
	 * All zeros.
	 */
	public CompressedBitmap() {
		this(false);
	}
	
	public CompressedBitmap(EWAHCompressedBitmap underlying) {
		this.underlying = underlying;
	}

	/**
	 * If true, all bits are 1. Otherwise all bits
	 * are 0.
	 */
	public CompressedBitmap(boolean allOnes) {
		if(allOnes) {
			underlying = EwahUtil.ALL_ONES;
		} else {
			underlying = new EWAHCompressedBitmap();
		}
	}
	
	public CompressedBitmap(boolean[] values) {
		if(values.length > Constants.ROWS_PER_PAGE) {
			throw new IllegalArgumentException();
		}
		underlying = new EWAHCompressedBitmap();
		for (int i = 0; i < values.length; i++) {
			if(values[i]) {				
				underlying.set(i);
			}
		}
	}
	
	@Override
	public Kind getKind() {
		return Kind.EWAH;
	}
	
	@Override
	public void set(int index) {
		underlying.set(index);
	}

	@Override
	public boolean isEmpty() {
		return !underlying.and(EwahUtil.ALL_ONES).iterator().hasNext();
	}

	@Override
	public boolean isFull() {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean isSet(int index) {
		EWAHCompressedBitmap bitmap = new EWAHCompressedBitmap();
		bitmap.set(index);
		return bitmap.and(underlying).intIterator().hasNext();
	}

	@Override
	@SuppressWarnings("unchecked")
	public EWAHCompressedBitmap getUnderlying() {
		return underlying;
	}

	@Override
	public long sizeInBytes() {
		return 8 + underlying.sizeInBytes();
	}
}
