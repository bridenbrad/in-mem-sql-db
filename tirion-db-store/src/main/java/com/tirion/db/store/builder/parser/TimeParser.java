/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.parser;

import java.sql.Time;
import java.text.SimpleDateFormat;

import com.tirion.common.date.DateUtil;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.array.NativeArray;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TimeParser extends AbstractParser {

	private final String pattern;
	
	public TimeParser(String nullValue, String pattern) {
		super(nullValue);
		this.pattern = pattern;
	}

	@Override
	public Array parse(String[][] splits, int startRow, int endRow, int columnIndex) {
		try {
			return parseInternal(splits, startRow, endRow, columnIndex);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Array parseInternal(String[][] splits, int startRow, int endRow, int columnIndex) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		
		final int rowCount = endRow - startRow;
		
		final boolean[] nulls = super.getNulls(splits, startRow, endRow, columnIndex);
		
		int[] result = new int[rowCount];
		int index = 0;
		if(nulls == null) {
			for (int i = startRow; i < endRow; i++) {
				result[index++] = parseTime(splits[i][columnIndex], formatter);
			}	
		} else {
			for (int i = startRow; i < endRow; i++) {
				if(!nulls[i]) {					
					result[index++] = parseTime(splits[i][columnIndex], formatter);
				}
			}
		}
		return new NativeArray(new Spec(Type.INT, result.length, false, getBitmap(nulls)), result);
	}
	
	private int parseTime(String value, SimpleDateFormat formatter) throws Exception {
		java.util.Date date = formatter.parse(value);
		Time time = new Time(date.getTime());
		return DateUtil.getTimeNumberFromSqlTime(time);
	}
}
