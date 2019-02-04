/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.sum;

import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class IntSumFunctor extends SumFunctor {

	private long sum = 0;

	public IntSumFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public int getWidth() {
		return Type.LONG.getWidth();
	}
	
	@Override
	protected void onTupleInternal(Tuple tuple) {
		sum += tuple.getInteger(getInputIndex());
	}

	@Override
	public void writeStateTo(Tuple tuple) {
		tuple.putLong(getOutputIndex(), sum);
	}

	@Override
	public Functor cloneMe() {
		return new IntSumFunctor(getInputIndex(), getOutputIndex());
	}
}
