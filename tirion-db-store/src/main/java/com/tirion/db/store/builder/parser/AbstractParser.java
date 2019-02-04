/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.parser;

import com.tirion.common.bitmap.ArrayBackedBitmap;
import com.tirion.common.bitmap.Bitmap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractParser implements Parser {

	private final String nullString;
	
	public AbstractParser(String nullString) {
		this.nullString = nullString;
	}

	protected final boolean isNull(String value) {
		return nullString.equals(value);
	}
	
	protected final boolean[] getNulls(String[][] splits, int startRow, int endRow, final int columnIndex) {
		final int rowCount = endRow - startRow;
		
		boolean hasNulls = false;
		boolean[] nulls = new boolean[rowCount];
		for (int i = startRow; i < endRow; i++) {
			if(isNull(splits[i][columnIndex])) {
				nulls[i] = true;
				hasNulls = true;
			}			
		}
		if(hasNulls) {
			return nulls;
		} else {
			return null;
		}
	}
	
	protected final Bitmap getBitmap(boolean[] nulls) {
		return nulls != null ? new ArrayBackedBitmap(nulls) : null; // TODO make it generic whether it is array or bit-based
	}
}
