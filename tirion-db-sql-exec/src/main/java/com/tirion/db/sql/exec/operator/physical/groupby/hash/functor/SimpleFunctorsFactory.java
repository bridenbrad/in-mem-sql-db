/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.groupby.hash.functor;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleFunctorsFactory implements FunctorsFactory {

	private final Functors seed;
	
	public SimpleFunctorsFactory(Functors seed) {
		super();
		this.seed = seed;
	}

	@Override
	public Functors newFunctors() {
		Functors result= seed.cloneMe();
		result.init();
		return result;
	}
}
