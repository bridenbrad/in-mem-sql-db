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
public final class FloatSumFunctor extends SumFunctor {

	private double sum = 0;

	public FloatSumFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public int getWidth() {
		return Type.DOUBLE.getWidth();
	}

	@Override
	protected void onTupleInternal(Tuple tuple) {
		sum += tuple.getFloat(getInputIndex());
	}

	@Override
	public void writeStateTo(Tuple tuple) {
		tuple.putDouble(getOutputIndex(), sum);
	}

	@Override
	public Functor cloneMe() {
		return new FloatSumFunctor(getInputIndex(), getOutputIndex());
	}
}
