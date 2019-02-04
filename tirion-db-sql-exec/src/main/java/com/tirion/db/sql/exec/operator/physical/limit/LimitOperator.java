/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.limit;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.exec.operator.physical.SingleSourcePhysicalOperator;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LimitOperator extends SingleSourcePhysicalOperator {

	@JsonProperty
	private int maxCount;
	private int currentCount;
	
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	@Override
	public Kind getKind() {
		return Kind.LIMIT;
	}

	@Override
	public void init() {
		super.init();
		currentCount = 0;
	}

	@Override
	protected Tuple nextInternal() {
		Tuple tuple = nextFromSource();
		if(tuple == null) {
			setDone();
			return null;
		}
		++currentCount;
		if(currentCount == maxCount) {
			setDone();
		}
		return tuple;
	}
}
