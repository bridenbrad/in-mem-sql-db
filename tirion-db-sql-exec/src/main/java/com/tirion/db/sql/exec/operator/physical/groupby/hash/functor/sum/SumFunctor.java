/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.sum;

import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.AbstractFunctor;

/**
 * Result is of type {@link Long}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class SumFunctor extends AbstractFunctor {

	public SumFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}
}
