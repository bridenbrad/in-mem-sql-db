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

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.sequence.buffer.Buffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class OffHeapTuple implements Tuple {

	private boolean[] nulls;
	private Buffer buffer;
	
	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isNull(int index) {
		return nulls != null && nulls[index];
	}

	@Override
	public void setNull(int index) {
		
	}

	@Override
	public Object[] getUnderlying() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(int index, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putBoolean(int index, boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putByte(int index, byte value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putShort(int index, short value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putInteger(int index, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putLong(int index, long value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putFloat(int index, float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putDouble(int index, double value) {
		
	}

	@Override
	public void putString(int index, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putTimestamp(int index, Timestamp value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putDate(int index, Date value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void putTime(int index, Time value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object get(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public boolean getBoolean(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public byte getByte(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public short getShort(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public int getInteger(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public long getLong(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public float getFloat(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public double getDouble(int index) {
//		return ((ByteBuffer)buffer.getUnderlying()).getDouble(getPositionFor(index));
		throw new NotYetImplementedException();
	}

	@Override
	public String getString(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public Timestamp getTimestamp(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public Date getDate(int index) {
		throw new NotYetImplementedException();
	}

	@Override
	public Time getTime(int index) {
		throw new NotYetImplementedException();
	}
	
	private int getPositionFor(int index) {
		return 0;
	}
}
