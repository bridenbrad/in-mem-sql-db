/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor;

import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.count.CountFunctor;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.sql.exec.tuple.Tuples;

/**
 * Root of all {@link Functor}s that accept single parameter.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractFunctor implements Functor {

	private final int inputIndex;
	private final int outputIndex;

	public AbstractFunctor(int inputIndex, int outputIndex) {
		this.inputIndex = inputIndex;
		this.outputIndex = outputIndex;
	}

	protected final int getInputIndex() {
		return inputIndex;
	}

	protected final int getOutputIndex() {
		return outputIndex;
	}
	
	/**
	 * Should be overridden by functors that do not
	 * need any column from input tuple, such as
	 * {@link CountFunctor}.
	 */
	@Override
	public void onTuple(Tuple tuple) {
		if(tuple.isNull(getInputIndex())) {
			return;
		}
		onTupleInternal(tuple);
	}

	@Override
	public final void onTuples(Tuples tuples) {
		throw new UnsupportedOperationException();
	}

	protected abstract void onTupleInternal(Tuple tuple);
}
