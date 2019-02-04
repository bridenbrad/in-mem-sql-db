/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.visitor;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.common.EntityRef;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FunctionCall;
import com.tirion.db.sql.ast.common.Star;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.common.constant.ConstantRange;
import com.tirion.db.sql.ast.common.constant.ConstantSet;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.compare.BetweenCompare;
import com.tirion.db.sql.ast.expression.compare.Compare;
import com.tirion.db.sql.ast.expression.compare.InCompare;
import com.tirion.db.sql.ast.expression.compare.NullCompare;
import com.tirion.db.sql.ast.expression.compare.SimpleCompare;
import com.tirion.db.sql.ast.select.FromClause;
import com.tirion.db.sql.ast.select.GroupByClause;
import com.tirion.db.sql.ast.select.HavingClause;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.select.JoinClause;
import com.tirion.db.sql.ast.select.LimitClause;
import com.tirion.db.sql.ast.select.OrderByClause;
import com.tirion.db.sql.ast.select.SelectClause;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.select.WhereClause;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractVisitor implements Visitor {
	
	private final Runtime runtime;
	
	public AbstractVisitor(Runtime runtime) {
		this.runtime = runtime;
	}

	protected final Runtime getRuntime() {
		return runtime;
	}

	@Override
	public void visit(Node node, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(SelectStatement select, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(SelectClause select, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(FromClause from, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}
	
	@Override
	public void visit(JoinClause joinClause, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(Join join, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}
	
	@Override
	public void visit(WhereClause where, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(GroupByClause group, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(HavingClause having, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(OrderByClause order, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(LimitClause limit, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}
	
	@Override
	public void visit(BoolExpression expression, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(Compare compare, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(SimpleCompare compare, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(InCompare in, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(BetweenCompare between, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}
	
	@Override
	public void visit(NullCompare nullCompare, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(EntityRef entityRef, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(Star star, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(FieldRef fieldRef, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}
	
	@Override
	public void visit(FunctionCall call, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(Constant constant, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(ConstantRange range, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}

	@Override
	public void visit(ConstantSet set, Context ctx) {
		throw new IllegalStateException("Method should be overridden");
	}
	
//	/**
//	 * @param visitor what to apply
//	 * @param ctx under what context
//	 * @param root on what to apply it
//	 */
//	protected final void apply(Visitor visitor, Context ctx, Node root, Scope scope, Order order) {
//		DelegatingVisitor delegator = new DelegatingVisitor(scope, order, visitor);
//		delegator.visit(root, ctx);
//	}
}
