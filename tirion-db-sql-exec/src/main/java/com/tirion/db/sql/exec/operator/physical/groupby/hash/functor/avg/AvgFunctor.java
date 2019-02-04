/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.avg;

import com.tirion.db.sql.exec.operator.physical.groupby.hash.functor.AbstractFunctor;

/**
 * Result is {@link Double} type.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AvgFunctor extends AbstractFunctor {

	public AvgFunctor(int inputIndex, int outputIndex) {
		super(inputIndex, outputIndex);
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}
}
