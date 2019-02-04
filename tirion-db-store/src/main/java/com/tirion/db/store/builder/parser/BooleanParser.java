/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.parser;

import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.array.NativeArray;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BooleanParser extends AbstractParser {

	public BooleanParser(String nullString) {
		super(nullString);
	}

	@Override
	public Array parse(String[][] splits, int startRow, int endRow, int columnIndex) {
		final int rowCount = endRow - startRow;
		
		final boolean[] nulls = super.getNulls(splits, startRow, endRow, columnIndex);
		
		byte[] result = new byte[rowCount];
		int index = 0;
		if(nulls == null) {
			for (int i = startRow; i < endRow; i++) {
				final String value = splits[i][columnIndex];
				if(value.equals("1") || value.equalsIgnoreCase("true")) {				
					result[index] = (byte)1;
				} else {
					result[index] = (byte)0;
				}
			}	
		} else {
			for (int i = startRow; i < endRow; i++) {
				if(!nulls[i]) {
					final String value = splits[i][columnIndex];
					if(value.equals("1") || value.equalsIgnoreCase("true")) {				
						result[index] = (byte)1;
					} else {
						result[index] = (byte)0;
					}	
				}
			}
		}
		return new NativeArray(new Spec(Type.BYTE, result.length, false, getBitmap(nulls)), result);
	}
}
