/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model.function;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.type.Type;

/**
 * Function descriptor. See {@link Functor} for executable
 * form of function. <p>
 * 
 * Null is used to represent * parameter, such as count(*).
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Function {

	@JsonProperty
	String getName();
	
	boolean areParametersValid(List<Type> params);
	
	/**
	 * Used for generic functions which are applicable
	 * for different input parameter types.
	 */
	Type getReturnType(List<Type> params);
	
	@JsonProperty
	int getParameterCount();
	
//	Functor buildFunctor(List<Type> params, int inputIndex, int outputIndex);
}
