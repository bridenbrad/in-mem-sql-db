/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash;

import java.util.LinkedList;

import com.tirion.db.sql.exec.operator.physical.common.key.KeyBuilder;
import com.tirion.db.sql.exec.operator.physical.groupby.GroupByOperator;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.grouper.Grouper;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class HashGroupByOperator extends GroupByOperator {

	private Grouper grouper;
	private KeyBuilder keyBuilder;
	
	private boolean isSourceConsumed;
	private boolean isOutputBuilt;
	
	private LinkedList<Tuple> tuples;
	
	public void setGrouper(Grouper grouper) {
		this.grouper = grouper;
	}
	
	public void setKeyBuilder(KeyBuilder keyBuilder) {
		this.keyBuilder = keyBuilder;
	}

	@Override
	protected Tuple nextInternal() {
		if(!isSourceConsumed) {
			consumeSource();
		}	
		if(!isOutputBuilt) {
			buildOutput();
		}
		if(tuples.isEmpty()) {
			return null;
		}
		return tuples.removeFirst();
	}
	
	private void buildOutput() {
		tuples = grouper.getResult();
		isOutputBuilt = true;
	}
	
	private void consumeSource() {
		while(true) {
			Tuple tuple = nextFromSource();
			if(tuple == null) {
				grouper.onTuple(null, null);
				break;
			}
			grouper.onTuple(tuple, keyBuilder.build(tuple));
		}
		isSourceConsumed = true;
	}
}
