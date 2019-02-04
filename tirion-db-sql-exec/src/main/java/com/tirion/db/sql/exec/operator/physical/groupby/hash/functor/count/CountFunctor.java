/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.count;

import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.AbstractFunctor;
import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.Functor;
import com.tirion.db.sql.exec.tuple.Tuple;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CountFunctor extends AbstractFunctor {

	private long count;
	
	public CountFunctor(int outputIndex) {
		super(Integer.MIN_VALUE, outputIndex);
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public int getWidth() {
		return Type.LONG.getWidth();
	}
	
	@Override
	public void onTuple(Tuple tuple) {
		onTupleInternal(tuple);
	}

	@Override
	protected void onTupleInternal(Tuple tuple) {
		++count;
	}

	@Override
	public void writeStateTo(Tuple tuple) {
		tuple.putLong(getOutputIndex(), count);
	}
	
	@Override
	public Functor cloneMe() {
		return new CountFunctor(getOutputIndex());
	}
}
