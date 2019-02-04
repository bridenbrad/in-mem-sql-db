/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.common.key;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class EmptyKey implements Key {
	
	public static final Key EMPTY_KEY = new EmptyKey();
	
	private EmptyKey() {
		super();
	}

	@Override
	public boolean isNull() {
		return false;
	}

	@Override
	public void writeStateTo(Tuple tuple) {
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
}
