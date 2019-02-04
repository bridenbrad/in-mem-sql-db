/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class DualSourcePhysicalOperator extends AbstractPhysicalOperator {
	
	@JsonProperty
	private PhysicalOperator left;
	@JsonProperty
	private PhysicalOperator right;
	
	public final void setLeftSource(PhysicalOperator left) {
		this.left = left;
	}

	public final void setRightSource(PhysicalOperator right) {
		this.right = right;
	}

	protected final Tuple nextFromLeft() {
		return left.next();
	}
	
	protected final Tuple nextFromRight() {
		return right.next();
	}

	@Override
	public void init() {
		left.init();
		right.init();
	}

	@Override
	public void shutdown() {
		right.shutdown();
		left.shutdown();
	}
}
