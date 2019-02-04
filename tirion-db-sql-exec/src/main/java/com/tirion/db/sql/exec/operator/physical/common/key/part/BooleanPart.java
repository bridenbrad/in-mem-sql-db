/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.common.key.part;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BooleanPart implements Part {
	
	private final boolean value;
	private final boolean isNull;

	public BooleanPart(boolean value, boolean isNull) {
		this.value = value;
		this.isNull = isNull;
	}
	
	@Override
	public void writeStateTo(int index, Tuple tuple) {
		tuple.putBoolean(index, value);
	}

	@Override
	public boolean isNull() {
		return isNull;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isNull ? 1231 : 1237);
		result = prime * result + (value ? 1 : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BooleanPart other = (BooleanPart) obj;
		if(isNull && other.isNull) {
			return true;
		}
		if(isNull || other.isNull) {
			return false;
		}
		if (value != other.value) {
			return false;
		}
		return true;
	}
}