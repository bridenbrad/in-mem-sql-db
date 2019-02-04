/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog;

import java.util.ArrayList;
import java.util.List;

import com.tirion.db.catalog.model.function.AvgFunction;
import com.tirion.db.catalog.model.function.CountDistinctFunction;
import com.tirion.db.catalog.model.function.CountFunction;
import com.tirion.db.catalog.model.function.Function;
import com.tirion.db.catalog.model.function.MaxFunction;
import com.tirion.db.catalog.model.function.MinFunction;
import com.tirion.db.catalog.model.function.SumFunction;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractCatalog implements Catalog {
	
	private final List<Function> functions;
	
	public AbstractCatalog() {
		functions = getAllSupportedFunctions();
	}

	@Override
	public final Function getFunction(String functionName) {
		for(Function function : functions) {
			if(function.getName().equalsIgnoreCase(functionName)) {
				return function;
			}
		}
		throw new IllegalArgumentException("Unknown function '" + functionName + "'");
	}

	@Override
	public final boolean hasFunction(String functionName) {
		for(Function function : functions) {
			if(function.getName().equalsIgnoreCase(functionName)) {
				return true;
			}
		}
		return false;
	}
	
	private List<Function> getAllSupportedFunctions() {
		List<Function> result = new ArrayList<Function>();
		result.add(new MinFunction());
		result.add(new MaxFunction());
		result.add(new SumFunction());
		result.add(new AvgFunction());
		result.add(new CountFunction());
		result.add(new CountDistinctFunction());
		return result;
	}
}
