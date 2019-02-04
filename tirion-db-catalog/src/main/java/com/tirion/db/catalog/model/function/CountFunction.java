/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model.function;

import java.util.List;

import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CountFunction implements Function {

	@Override
	public String getName() {
		return "count";
	}

	@Override
	public boolean areParametersValid(List<Type> params) {
		if(params.size() != 1) {
			return false;
		}
		return params.get(0) == null;
	}

	@Override
	public Type getReturnType(List<Type> params) {
		if(!areParametersValid(params)) {
			throw new IllegalArgumentException();
		}
		return Type.LONG;
	}
	
	@Override
	public int getParameterCount() {
		return 1;
	}
	
//	@Override
//	public Functor buildFunctor(List<Type> params, int inputIndex, int outputIndex) {
//		return new CountFunctor(outputIndex);
//	}
}
