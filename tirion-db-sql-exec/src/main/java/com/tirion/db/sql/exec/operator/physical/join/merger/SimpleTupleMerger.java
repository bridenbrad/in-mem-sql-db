/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.join.merger;

import com.tirion.db.sql.exec.tuple.OnHeapTuple;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Will just merge underlying arrays into one array.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleTupleMerger implements TupleMerger {

	@Override
	public Tuple merge(Tuple left, Tuple right) {
		Object[] result = new Object[left.getFieldCount() + right.getFieldCount()];
		
		Object[] leftArr = left.getUnderlying();
		System.arraycopy(leftArr, 0, result, 0, leftArr.length);
		
		Object[] rightArr = right.getUnderlying();
		System.arraycopy(result, 0, result, leftArr.length, rightArr.length);
		
		return new OnHeapTuple(result);
	}
}
