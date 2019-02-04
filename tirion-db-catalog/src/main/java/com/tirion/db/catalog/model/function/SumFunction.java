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
public final class SumFunction implements Function {

	@Override
	public String getName() {
		return "sum";
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
		Type type = params.get(0);
		if(type == Type.DOUBLE || type == Type.FLOAT) {
			return Type.DOUBLE;
		}
		return Type.LONG;
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
//				return new ByteSumFunctor(inputIndex, outputIndex);
//			case SHORT:
//				return new ShortSumFunctor(inputIndex, outputIndex);
//			case INT:
//				return new IntSumFunctor(inputIndex, outputIndex);
//			case LONG:
//				return new LongSumFunctor(inputIndex, outputIndex);
//			case FLOAT:
//				return new FloatSumFunctor(inputIndex, outputIndex);
//			case DOUBLE:
//				return new DoubleSumFunctor(inputIndex, outputIndex);
//			default:
//				throw new IllegalArgumentException(param.toString());
//		}
//	}
}
