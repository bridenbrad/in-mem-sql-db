/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple.sink;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BufferingTupleListener extends AbstractTupleListener {

	private final List<Tuple> tuples = new LinkedList<Tuple>();
	
	public List<Object[]> getArrays() {
		List<Object[]> list = new ArrayList<Object[]>(tuples.size());
		for(Tuple tuple : tuples) {
			list.add(tuple.getUnderlying());
		}
		return list;
	}
	
	public List<Tuple> getTuples() {
		return tuples;
	}

	@Override
	public void onTuple(Tuple tuple) {
		tuples.add(tuple);
	}
}
