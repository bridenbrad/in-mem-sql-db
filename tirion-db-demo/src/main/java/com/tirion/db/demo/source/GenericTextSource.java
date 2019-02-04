/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.demo.source;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import com.tirion.common.date.DateUtil;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.common.Config;
import com.tirion.db.demo.nullify.NullDecider;

/**
 * TODO add support for other types, better value distribution etc.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GenericTextSource implements TextSource {
	
	private final SimpleDateFormat ISO_DATE_FORMATTER = new SimpleDateFormat(Config.DEFAULT_ISO_DATE_PATTERN);
	private final SimpleDateFormat ISO_TIMESTAMP_FORMATTER = new SimpleDateFormat(Config.DEFAULT_ISO_TIMESTAMP_PATTERN);
	private final SimpleDateFormat ISO_TIME_FORMATTER = new SimpleDateFormat(Config.DEFAULT_ISO_TIME_PATTERN);
	
	private final Random random = new Random(1234567890L);

	private final int maxCount;
	private final Entity entity;
	private final String middleString;
	private final NullDecider nullDecider;
	private final String valueSeparator;
	private final String nullString;

	private int count = 0;
	
	public GenericTextSource(int maxCount, Entity entity, String middleString, NullDecider nullDecider, String valueSeparator, String nullString) {
		super();
		this.maxCount = maxCount;
		this.entity = entity;
		this.middleString = middleString;
		this.nullDecider = nullDecider;
		this.valueSeparator = valueSeparator;
		this.nullString = nullString;
	}

	@Override
	public String next() {
		if(count == maxCount) {
			return null;
		}
		++count;
		StringBuilder builder = new StringBuilder();
		final int fieldCount = entity.fieldCount();
		for (int i = 0; i < fieldCount; i++) {
			Field field = entity.getField(i);
			Object value = null;
			switch (field.getDeclaredType()) {
				case BOOLEAN:
					value = nextBoolean(field);
					break;
				case BYTE:
					value = nextByte(field);
					break;
				case SHORT:
					value = nextShort(field);
					break;
				case INT:
					value = nextInt(field);
					break;
				case LONG:
					value = nextLong(field);
					break;
				case FLOAT:
					value = nextFloat(field);
					break;
				case DOUBLE:
					value = nextDouble(field);
					break;
				case VARCHAR:
					value = nextStr(field);
					break;
				case DATE:
					value = nextDate(field);
					break;
				case TIME:
					value = nextTime(field);
					break;
				case TIMESTAMP:
					value = nextTimestamp(field);
					break;
				default:
					throw new IllegalArgumentException("Generic text generator does not support type '" + field.getDeclaredType() + "'");
			}
			if(value == null) {
				builder.append(nullString);						
			} else {
				builder.append(value);
			}
			if(i < fieldCount - 1) {
				builder.append(valueSeparator);
			}
		}
		return builder.toString();
	}

	private String nextStr(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		int value = random.nextInt(getDistinct(field));
		return value + "_" + middleString + "_" + value;
	}
	
	private Boolean nextBoolean(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		return random.nextBoolean();
	}
	
	private Byte nextByte(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		return (byte) random.nextInt(getDistinct(field));
	}
	
	private Short nextShort(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		return (short) random.nextInt(getDistinct(field));
	}
	
	private Integer nextInt(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		return (int) random.nextInt(getDistinct(field));
	}
	
	private Long nextLong(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		return (long) random.nextInt(getDistinct(field));
	}
	
	private Float nextFloat(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		return (float) random.nextInt(getDistinct(field));
	}
	
	private Double nextDouble(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		return (double) random.nextInt(getDistinct(field));
	}
	
	private String nextDate(Field field) {
		if(field.getOptions().isNullable() && nullDecider.isNull()) {
			return null;
		}
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.set(1900 + 113, random.nextInt(4), random.nextInt(26));
		short num = DateUtil.timestampToDayNumber(calendar.getTimeInMillis());
		return ISO_DATE_FORMATTER.format(DateUtil.dayNumberToTimestamp(num));
	}
	
	private String nextTimestamp(Field field) {
		return ISO_TIMESTAMP_FORMATTER.format(new Date());
	}
	
	private String nextTime(Field field) {
		Calendar calendar = Calendar.getInstance();
		return ISO_TIME_FORMATTER.format(calendar.getTime());
	}
	
	private int getDistinct(Field field) {
		if(field.getOptions().getDistinct() > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		return (int) field.getOptions().getDistinct();
	}
}
