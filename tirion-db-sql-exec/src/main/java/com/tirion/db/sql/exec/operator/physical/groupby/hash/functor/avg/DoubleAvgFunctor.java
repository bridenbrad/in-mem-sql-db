/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.avg;

import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.AbstractFunctor;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DoubleAvgFunctor extends AvgFunctor {

	private double sum = 0;
	private long count = 0;

	public DoubleAvgFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public int getWidth() {
		return Type.DOUBLE.getWidth();
	}
	
	@Override
	protected void onTupleInternal(Tuple tuple) {
		sum += tuple.getDouble(getInputIndex());
		++count;
	}
	
	@Override
	public void writeStateTo(Tuple tuple) {
		double result = 0;
		if(count > 0) {
			result = ((double)sum / (double)count);
		}
		tuple.putDouble(getOutputIndex(), result);
	}

	@Override
	public AbstractFunctor cloneMe() {
		return new DoubleAvgFunctor(getInputIndex(), getOutputIndex());
	}
}
