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
import com.tirion.stats.Statistics;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractPhysicalOperator implements PhysicalOperator {

	@JsonProperty
	private int id;
//	@JsonProperty
//	private Tx tx;
	@JsonProperty
	private boolean isDone;
	
//	private Job job;

	@Override
	public Statistics getStatistics() {
		return null;
	}
	
	/**
	 * True if there is tx associated with
	 * calculation.
	 */
//	protected final boolean hasTx() {
//		return tx != null;
//	}
//	
//	protected final Tx getTx() {
//		return tx;
//	}
//
//	public final void setTx(Tx tx) {
//		this.tx = tx;
//	}

	@Override
	public final int getId() {
		return id;
	}
	
	public final void setId(int id) {
		this.id = id;
	}

//	public final void setJob(Job job) {
//		this.job = job;
//	}
//
//	protected final Job getJob() {
//		return job;
//	}
//
//	protected final Runtime getRuntime() {
//		return job.getRuntime();
//	}
	
	/**
	 * If true, this operator is done and calls to
	 * next() will produce null.
	 */
	protected final boolean isDone() {
		return isDone;
	}

	/**
	 * Marks this operator as done.
	 */
	protected final void setDone() {
		this.isDone = true;
	}
	
	@Override
	public final Tuple next() {
		if(isDone()) {
			return null;
		}
		return nextInternal();
	}

	/**
	 * Abstract class will call isDone() to check if
	 * this operator is done. If this operator is not 
	 * done, then this callback will be called.
	 */
	protected abstract Tuple nextInternal();
}
