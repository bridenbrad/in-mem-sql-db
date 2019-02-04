/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.sql.ast.Node.Kind;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.constant.BaseConstant;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.common.constant.ConstantRange;
import com.tirion.db.sql.ast.common.constant.ConstantSet;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.bool.CompositeBoolExpression;
import com.tirion.db.sql.ast.expression.compare.BetweenCompare;
import com.tirion.db.sql.ast.expression.compare.InCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare;
import com.tirion.db.sql.ast.expression.compare.SimpleCompare;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BoolExpressionEnricher extends AbstractVisitor {
	
	public BoolExpressionEnricher(Runtime runtime) {
		super(runtime);
	}
	
	@Override
	public void visit(NullCompare nullCompare, Context ctx) {
		// noop
	}

	@Override
	public void visit(BoolExpression expression, Context ctx) {
		if(expression instanceof CompositeBoolExpression) {
			for(BoolExpression child : ((CompositeBoolExpression)expression).getChildren()) {
				visit(child, ctx);
			}
		} else if(expression instanceof BetweenCompare) {
			visit((BetweenCompare)expression, ctx);
		} else if(expression instanceof InCompare) {
			visit((InCompare)expression, ctx);
		} else if(expression instanceof SimpleCompare) {
			visit((SimpleCompare)expression, ctx);
		} else if(expression instanceof NullCompare) {
			visit((NullCompare)expression, ctx);
		} else {
			throw new IllegalArgumentException("Unexpected node kind '" + expression.getKind() + "'");
		}
	}
	
	@Override
	public void visit(SimpleCompare compare, Context ctx) {
		FieldRef left = compare.getFieldRef();
//		visit(left, ctx);
		if(ctx.isInHavingScope() || ctx.isInWhereScope()) {
			if(!(compare.getRight() instanceof BaseConstant)) {
				ctx.getListener().onMessage("RHS must be constant in WHERE or HAVING clause", MessageKind.ERROR);
			}
		}
		if(compare.getRight().getKind() == Kind.FIELD_REF) {
			if(!ctx.isInJoinScope()) {
				ctx.getListener().onMessage("Expected JOIN scope", MessageKind.ERROR);
			}
			FieldRef right = (FieldRef) compare.getRight();
//			visit(right, ctx);
			if(left.getOwner().equals(right.getOwner())) {
				ctx.getListener().onMessage("Self join not supported", MessageKind.ERROR);
			}
			if(left.getType() != right.getType()) {
				ctx.getListener().onMessage("Joined columns have different types", MessageKind.ERROR);
			}
		} else {
			visit((Constant)compare.getRight(), ctx);
		}
		// ensure that STRING columns only use = and !=
		if(left.getType() == Type.VARCHAR || left.getType() == Type.BOOLEAN) {			
			if(compare.getKind() != Kind.EQ_OPERATOR && compare.getKind() != Kind.NEQ_OPERATOR) {
				ctx.getListener().onMessage("STRING & BOOLEAN types support only = and != operators", MessageKind.ERROR);
			}
		}
	}

	@Override
	public void visit(InCompare in, Context ctx) {
		if((ctx.isInHavingScope() || ctx.isInJoinScope()) && !in.isConstant()) {
			ctx.getListener().onMessage("Sub-query based IN can not be used in HAVING or JOIN clauses", MessageKind.ERROR);
		}
//		visit(in.getFieldRef(), ctx);
		if(in.getFieldRef().getType() == Type.BOOLEAN) {
			ctx.getListener().onMessage("Column of type BOOLEAN can not be used in IN clause", MessageKind.ERROR);
		}
		if(in.isConstant()) {
			visit(in.getSet(), ctx);
		} else {
			Entity output = in.getSelect().getOutput(); 
			if(output.fieldCount() != 1) {
				// this should be done in sub-select rather than here...
				ctx.getListener().onMessage("IN clause sub-query must return single column", MessageKind.ERROR);
			}
			if(in.getFieldRef().getType() != output.getField(0).getDeclaredType()) {
				ctx.getListener().onMessage("IN clause sub-query has wrong return type", MessageKind.ERROR);
			}
		}
	}
	
	

	@Override
	public void visit(BetweenCompare between, Context ctx) {
//		visit(between.getFieldRef(), ctx);
		visit(between.getRange(), ctx);
		if(between.getFieldRef().getType() == Type.VARCHAR || between.getFieldRef().getType() == Type.BOOLEAN) {
			ctx.getListener().onMessage("Column of type STRING or BOOLEAN can not be used in between clause", MessageKind.ERROR);
		}
		// TODO verify that low <= high
	}

	@Override
	public void visit(Constant constant, Context ctx) {
		Type type = constant.getParent(SimpleCompare.class).getFieldRef().getType();
		constant.setValue(BaseConstant.parseFromString(constant.getValue(), type, getRuntime()));
		constant.setType(type);
	}

	@Override
	public void visit(ConstantRange range, Context ctx) {
		Type type = range.getParent(BetweenCompare.class).getFieldRef().getType();
		range.setLow(BaseConstant.parseFromString(range.getLow(), type, getRuntime()));
		range.setHigh(BaseConstant.parseFromString(range.getHigh(), type, getRuntime()));
		range.setType(type);
	}

	@Override
	public void visit(ConstantSet set, Context ctx) {
		Type type = set.getParent(InCompare.class).getFieldRef().getType();
		for(Object str : set.getStrValues()) {
			set.append(BaseConstant.parseFromString(str, type, getRuntime()));
		}
		set.setType(type);
	}
}
