/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.common.key;

import java.util.Arrays;

import com.tirion.db.sql.exec.operator.physical.common.key.part.Part;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CompositeKey implements Key {

	private final Part[] parts;
	private final int hash;
	
	public CompositeKey(Part[] parts) {
		this.parts = parts;
		this.hash = hashMe();
	}

	@Override
	public boolean isNull() {
		return parts == null || parts.length == 0;
	}

	/**
	 * Calculated in constructor.
	 */
	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeKey other = (CompositeKey) obj;
		if (!Arrays.equals(parts, other.parts))
			return false;
		return true;
	}

	@Override
	public void writeStateTo(Tuple tuple) {
		int index = 0;
		for (int i = 0; i < parts.length; i++) {
			final Part part = parts[i];
			if(part.isNull()) {
				tuple.setNull(i);
			} else {
				part.writeStateTo(index, tuple);
			}
			++index;
		}
	}

	@Override
	public String toString() {
		return "CompositeKey [parts=" + Arrays.toString(parts) + "]";
	}
	
	private int hashMe() {		
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(parts);
		return result;
	}
}
