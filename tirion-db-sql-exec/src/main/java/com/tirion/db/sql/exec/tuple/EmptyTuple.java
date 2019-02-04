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
public final class EmptyTuple implements Tuple {
	
	/**
	 * End-of-stream tuple.
	 */
	public static final Tuple EOS_TUPLE = new EmptyTuple();
	
	private EmptyTuple() {
		super();
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public boolean isNull(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNull(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] getUnderlying() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void put(int index, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putBoolean(int index, boolean value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putByte(int index, byte value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putShort(int index, short value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putInteger(int index, int value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putLong(int index, long value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putFloat(int index, float value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putDouble(int index, double value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putString(int index, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putTimestamp(int index, Timestamp value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putDate(int index, Date value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putTime(int index, Time value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getBoolean(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getByte(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public short getShort(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getInteger(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getLong(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getFloat(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public double getDouble(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getString(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(int index) {
		throw new UnsupportedOperationException();
	}
}
