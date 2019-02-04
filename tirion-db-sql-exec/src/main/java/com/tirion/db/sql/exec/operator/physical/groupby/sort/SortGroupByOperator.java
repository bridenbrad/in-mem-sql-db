/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.sort;

import com.tirion.db.sql.exec.operator.physical.groupby.GroupByOperator;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * Suited for data sets that can not fit into memory.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SortGroupByOperator extends GroupByOperator {

	public SortGroupByOperator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void init() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shutdown() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Tuple nextInternal() {
		throw new UnsupportedOperationException();
	}	
}
