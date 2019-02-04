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
public abstract class AbstractTupleListener implements TupleListener {

	@Override
	public void onTuples(Tuples tuples) {
		for(Tuple tuple : tuples.get()) {
			onTuple(tuple);
		}
	}
}
