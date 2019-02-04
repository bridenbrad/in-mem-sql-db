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
import com.tirion.common.type.Types;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class MaxFunction implements Function {

	@Override
	public String getName() {
		return "max";
	}

	@Override
	public boolean areParametersValid(List<Type> params) {
		if(params.size() != 1) {
			return false;
		}
		return Types.isNumeric(params.get(0));
	}

	@Override
	public Type getReturnType(List<Type> params) {
		if(!areParametersValid(params)) {
			throw new IllegalArgumentException();
		}
		return params.get(0);
	}
	
	@Override
	public int getParameterCount() {
		return 1;
	}
	
//	@Override
//	public Functor buildFunctor(List<Type> params, int inputIndex, int outputIndex) {
//		final Type param = params.get(0);
//		switch (param) {
//			case BYTE:
//				return new ByteMaxFunctor(inputIndex, outputIndex);
//			case DATE:
//			case SHORT:
//				return new ShortMaxFunctor(inputIndex, outputIndex);
//			case TIME:
//			case INT:
//				return new IntegerMaxFunctor(inputIndex, outputIndex);
//			case TIMESTAMP:
//			case LONG:
//				return new LongMaxFunctor(inputIndex, outputIndex);
//			case FLOAT:
//				return new FloatMaxFunctor(inputIndex, outputIndex);
//			case DOUBLE:
//				return new DoubleMaxFunctor(inputIndex, outputIndex);
//			default:
//				throw new IllegalArgumentException(param.toString());
//		}
//	}
}