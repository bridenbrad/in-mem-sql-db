/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.orderby;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.exec.operator.physical.SingleSourcePhysicalOperator;
import com.tirion.db.sql.exec.tuple.comparator.TupleComparator;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class OrderByOperator extends SingleSourcePhysicalOperator {

	@JsonProperty
	private TupleComparator comparator;
	
	protected final TupleComparator getComparator() {
		return comparator;
	}

	public final void setComparator(TupleComparator comparator) {
		this.comparator = comparator;
	}

	@Override
	public final Kind getKind() {
		return Kind.ORDER_BY;
	}
}
