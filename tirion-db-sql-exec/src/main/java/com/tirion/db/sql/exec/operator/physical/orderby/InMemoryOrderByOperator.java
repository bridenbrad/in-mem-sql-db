/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.orderby;

import java.util.Collections;
import java.util.List;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InMemoryOrderByOperator extends OrderByOperator {

	private List<Tuple> outputTuples;
	private boolean isSourceConsumed = false;

	@Override
	public void shutdown() {
		outputTuples = null;
		super.shutdown();
	}

	@Override
	protected Tuple nextInternal() {
		if(!isSourceConsumed) {			
			outputTuples = consumeAllFromSource();
			Collections.sort(outputTuples, getComparator());
			isSourceConsumed = true;
		}
		if(outputTuples.isEmpty()) {
			setDone();
			return null;
		}
		return outputTuples.remove(0);
	}
}
