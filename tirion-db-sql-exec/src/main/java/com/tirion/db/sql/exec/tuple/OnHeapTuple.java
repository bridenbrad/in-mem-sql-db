/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class OnHeapTuple implements Tuple {

	private final Object[] values;
	
	public OnHeapTuple(int fieldCount) {
		values = new Object[fieldCount];
	}
	
	public OnHeapTuple(Object[] values) {
		if(values == null) {
			throw new NullPointerException();
		}
		this.values = values;
	}

	@Override
	public int getFieldCount() {
		return values.length;
	}
	
	@Override
	public void putTimestamp(int index, Timestamp value) {
		values[index] = value;
	}

	@Override
	public void putDate(int index, Date value) {
		values[index] = value;
	}

	@Override
	public void putTime(int index, Time value) {
		values[index] = value;
	}

	@Override
	public void put(int index, Object value) {
		values[index] = value;
	}

	@Override
	public Object get(int index) {
		return values[index];
	}

	@Override
	public void putBoolean(int index, boolean value) {
		values[index] = value;
	}

	@Override
	public void putByte(int index, byte value) {
		values[index] = value;
	}

	@Override
	public void putShort(int index, short value) {
		values[index] = value;
	}

	@Override
	public void putInteger(int index, int value) {
		values[index] = value;
	}

	@Override
	public void putLong(int index, long value) {
		values[index] = value;
	}

	@Override
	public void putFloat(int index, float value) {
		values[index] = value;
	}

	@Override
	public void putDouble(int index, double value) {
		values[index] = value;
	}

	@Override
	public void putString(int index, String value) {
		values[index] = value;
	}

	@Override
	public boolean getBoolean(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Boolean) values[index];
	}

	@Override
	public byte getByte(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Byte) values[index];
	}

	@Override
	public short getShort(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Short) values[index];
	}

	@Override
	public int getInteger(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Integer) values[index];
	}

	@Override
	public long getLong(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Long) values[index];
	}

	@Override
	public float getFloat(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Float) values[index];
	}

	@Override
	public double getDouble(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Double) values[index];
	}

	@Override
	public String getString(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (String) values[index];
	}
	
	@Override
	public Timestamp getTimestamp(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Timestamp) values[index];
	}

	@Override
	public Date getDate(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Date) values[index];
	}

	@Override
	public Time getTime(int index) {
		if(isNull(index)) {
			throw new NullPointerException();
		}
		return (Time) values[index];
	}

	@Override
	public boolean isNull(int index) {
		return values[index] == null;
	}

	@Override
	public void setNull(int index) {
		values[index] = null;
	}

	@Override
	public Object[] getUnderlying() {
		return values;
	}

	@Override
	public String toString() {
		return "Tuple[size=" + values.length + "]";
	}
}
