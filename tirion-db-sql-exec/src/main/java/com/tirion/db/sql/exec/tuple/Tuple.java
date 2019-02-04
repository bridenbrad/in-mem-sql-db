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
 * Getting null value will throw exception.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Tuple {
	
	int getFieldCount();
	
	/**
	 * Is field with given index null.
	 */
	boolean isNull(int index);
	
	/**
	 * Sets field within tuple at given index to null.
	 */
	void setNull(int index);
	
	Object[] getUnderlying();
	
	void put(int index, Object value);
	
	void putBoolean(int index, boolean value);
	
	void putByte(int index, byte value);
	
	void putShort(int index, short value);
	
	void putInteger(int index, int value);
	
	void putLong(int index, long value);
	
	void putFloat(int index, float value);
	
	void putDouble(int index, double value);
	
	void putString(int index, String value);
	
	/**
	 * Should be used only once tuple 'leaves' engine.
	 */
	void putTimestamp(int index, Timestamp value);
	
	/**
	 * Should be used only once tuple 'leaves' engine.
	 */
	void putDate(int index, Date value);
	
	/**
	 * Should be used only once tuple 'leaves' engine.
	 */
	void putTime(int index, Time value);
	
	Object get(int index);
	
	boolean getBoolean(int index);
	
	byte getByte(int index);
	
	short getShort(int index);
	
	int getInteger(int index);
	
	long getLong(int index);
	
	float getFloat(int index);
	
	double getDouble(int index);
	
	String getString(int index);
	
	Timestamp getTimestamp(int index);
	
	Date getDate(int index);
	
	Time getTime(int index);
}
