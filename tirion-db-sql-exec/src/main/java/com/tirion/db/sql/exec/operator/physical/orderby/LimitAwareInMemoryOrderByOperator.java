/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.orderby;

import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LimitAwareInMemoryOrderByOperator extends OrderByOperator {

	@JsonProperty
	private int limit;
	
	private boolean isSourceConsumed;
	private TreeSet<Tuple> set;
	
	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public void init() {
		super.init();
		isSourceConsumed = false;
		set = new TreeSet<Tuple>(getComparator());
	}
	
	@Override
	public void shutdown() {
		set = null;
		super.shutdown();
	}

	@Override
	protected Tuple nextInternal() {
		if(!isSourceConsumed) {
			consumeSource();
		}
		if(set.isEmpty()) {
			setDone();
			return null;
		}
		return set.pollFirst();
	}
	
	private void consumeSource() {
		while(true) {
			Tuple tuple = nextFromSource();
			if(tuple == null) {
				break;
			}
			set.add(tuple);
			if(set.size() > limit) {
				set.pollLast();
			}
		}
		isSourceConsumed = true;
	}
}
