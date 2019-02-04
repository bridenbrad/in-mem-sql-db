/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class SingleSourcePhysicalOperator extends AbstractPhysicalOperator {

	@JsonProperty
	private PhysicalOperator source;

	public final void setSource(PhysicalOperator source) {
		this.source = source;
	}
	
	/**
	 * Returns next tuple from source operator.
	 */
	protected final Tuple nextFromSource() {
		return source.next();
	}
	
	protected final List<Tuple> consumeAllFromSource() {
		List<Tuple> result = new LinkedList<Tuple>();
		while(true) {
			Tuple tuple = nextFromSource();
			if(tuple == null) {
				break;
			}
			result.add(tuple);
		}
		return result;
	}

	@Override
	public void init() {
		source.init();
	}

	@Override
	public void shutdown() {
		source.shutdown();
	}
}
