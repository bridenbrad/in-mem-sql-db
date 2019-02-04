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
public final class AvgFunction implements Function {

	@Override
	public String getName() {
		return "avg";
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
		return Type.DOUBLE;
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
//				return new ByteAvgFunctor(inputIndex, outputIndex);
//			case SHORT:
//				return new ShortAvgFunctor(inputIndex, outputIndex);
//			case INT:
//				return new IntAvgFunctor(inputIndex, outputIndex);
//			case LONG:
//				return new LongAvgFunctor(inputIndex, outputIndex);
//			case FLOAT:
//				return new FloatAvgFunctor(inputIndex, outputIndex);
//			case DOUBLE:
//				return new DoubleAvgFunctor(inputIndex, outputIndex);
//			default:
//				throw new IllegalArgumentException(param.toString());
//		}
//	}
}
