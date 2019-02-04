/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple.sink;

import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.Tuples;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class PrintingTupleListener implements TupleListener {

	@Override
	public void onTuple(Tuple tuple) {
		Object[] array = tuple.getUnderlying();
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if(i < array.length - 1) {
				System.out.print(",");
			}
		}
		System.out.println();
	}

	@Override
	public void onTuples(Tuples tuples) {
		for(Tuple tuple : tuples.get()) {
			onTuple(tuple);
		}
	}
}
