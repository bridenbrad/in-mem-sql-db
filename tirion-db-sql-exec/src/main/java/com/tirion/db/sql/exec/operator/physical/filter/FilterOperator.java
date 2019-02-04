/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.filter;

import com.tirion.db.sql.exec.operator.physical.SingleSourcePhysicalOperator;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.stats.Statistics;

/**
 * Filtering of tuples. Used for FILTER clause
 * and case when we cant filter column-wise.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FilterOperator extends SingleSourcePhysicalOperator {

	private TupleFilter filter;
	
	private long consumedFromSource;
	private long produced;

	public void setFilter(TupleFilter filter) {
		this.filter = filter;
	}
	@Override
	public Kind getKind() {
		return Kind.FILTER;
	}

	@Override
	public Statistics getStatistics() {
		return new FilterOperatorStatistics(consumedFromSource, produced);
	}
	
	@Override
	protected Tuple nextInternal() {
		while(true) {			
			Tuple tuple = nextFromSource();
			if(tuple == null) {
				setDone();
				return null;
			}
			++consumedFromSource;
			if(filter.matches(tuple)) {
				++produced;
				return tuple;
			}
		}
	}
}
