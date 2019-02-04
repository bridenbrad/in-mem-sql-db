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
public final class LongAvgFunctor extends AvgFunctor {

	private long sum = 0;
	private long count = 0;

	public LongAvgFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public int getWidth() {
		return Type.DOUBLE.getWidth();
	}
	
	@Override
	protected void onTupleInternal(Tuple tuple) {
		sum += tuple.getLong(getInputIndex());
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
		return new LongAvgFunctor(getInputIndex(), getOutputIndex());
	}
}
