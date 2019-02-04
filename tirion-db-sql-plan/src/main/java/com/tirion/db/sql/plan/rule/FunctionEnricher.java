/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.function.Function;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FunctionCall;
import com.tirion.db.sql.ast.common.Star;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Assigns alias & type to function calls. Ensures that functions exist.
 * Should be called after {@link FieldRef} enrichment.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FunctionEnricher extends AbstractVisitor {
	
	public FunctionEnricher(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(FunctionCall call, Context ctx) {
		if(!ctx.hasFunction(call.getName())) {
			ctx.getListener().onMessage("Unknown function '" + call.getName() + "'", MessageKind.ERROR);
		}
		if(!call.hasAlias()) {
			call.setAlias(ctx.nextUniqueFunctionAlias());
		}
		
		Function function = ctx.getFunction(call.getName());
		List<Type> parameterTypes = buildParameterTypes(call.getParameters());
		if(!function.areParametersValid(parameterTypes)) {
			ctx.getListener().onMessage("Invalid parameters passed to function '" + call.getName() + "'", MessageKind.ERROR);
		}
		call.setReturnType(function.getReturnType(parameterTypes));
		call.setFunction(function);
		call.setParameterTypes(parameterTypes);
	}
	
	private List<Type> buildParameterTypes(List<Node> parameters) {
		List<Type> result = new ArrayList<Type>();
		for(Node parameter : parameters) {
			if(parameter instanceof Star) {
				result.add(null);
			} else {
				FieldRef field = (FieldRef) parameter;
				result.add(field.getType());
			}
		}
		return result;
	}
}
