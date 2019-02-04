/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.visitor;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.Node.Kind;
import com.tirion.db.sql.ast.common.EntityRef;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FunctionCall;
import com.tirion.db.sql.ast.common.Star;
import com.tirion.db.sql.ast.common.constant.Constant;
import com.tirion.db.sql.ast.common.constant.ConstantRange;
import com.tirion.db.sql.ast.common.constant.ConstantSet;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.bool.CompositeBoolExpression;
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
 * Will navigate all {@link Node}s of AST and call underlying
 * actual visitor. This way underlying visitor does not have to
 * worry about how to reach specific node types within AST, 
 * delegator will take care of that.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DelegatingVisitor implements Visitor {
	
	public static enum Scope {
		/**
		 * Single SELECT statement.
		 */
		SINGLE_LEVEL,

		/**
		 * All SELECT statements at any level 
		 * nesting level.
		 */
		MULTI_LEVEL,
	}
	
	public static enum Order {
		PARENT_FIRST,
		CHILDREN_FIRST,
	}

	private final Scope scope;
	private final Order order;
	private final Visitor underlying;
	private final Kind kind;
	
	public DelegatingVisitor(Scope scope, Order order, Visitor underlying, Kind kind) {
		this.scope = scope;
		this.order = order;
		this.underlying = underlying;
		this.kind = kind;
	}
	
	@Override
	public void visit(Node node, Context ctx) {
		switch (node.getKind()) {
			case SELECT_STATEMENT:	
				visit((SelectStatement)node, ctx);
				break;
			case SELECT_CLAUSE:	
				visit((SelectClause)node, ctx);
				break;
			case FROM_CLAUSE:	
				visit((FromClause)node, ctx);
				break;
			case JOIN_CLAUSE:	
				visit((JoinClause)node, ctx);
				break;
			case JOIN:
				visit((Join)node, ctx);
				break;
			case WHERE_CLAUSE:
				visit((WhereClause)node, ctx);
				break;
			case GROUP_BY_CLAUSE:
				visit((GroupByClause)node, ctx);
				break;
			case HAVING_CLAUSE:	
				visit((HavingClause)node, ctx);
				break;
			case ORDER_BY_CLAUSE:	
				visit((OrderByClause)node, ctx);
				break;
			case LIMIT_CLAUSE:	
				visit((LimitClause)node, ctx);
				break;
			case ENTITY_REF:	
				visit((EntityRef)node, ctx);
				break;
			case FIELD_REF:	
				visit((FieldRef)node, ctx);
				break;
			case FUNCTION_CALL:
				visit((FunctionCall)node, ctx);
				break;
			case CONSTANT:	
				visit((Constant)node, ctx);
				break;
			case CONSTANT_RANGE:
				visit((ConstantRange)node, ctx);
				break;
			case CONSTANT_SET:	
				visit((ConstantSet)node, ctx);
				break;
			case STAR:	
				visit((Star)node, ctx);
				break;
			case AND_EXPRESSION:	
				visit((CompositeBoolExpression)node, ctx);
				break;
			case OR_EXPRESSION:	
				visit((CompositeBoolExpression)node, ctx);
				break;
			case EQ_OPERATOR:	
				visit((SimpleCompare)node, ctx);
				break;
			case NEQ_OPERATOR:	
				visit((SimpleCompare)node, ctx);
				break;
			case LT_OPERATOR:	
				visit((SimpleCompare)node, ctx);
				break;
			case LTEQ_OPERATOR:	
				visit((SimpleCompare)node, ctx);
				break;
			case GT_OPERATOR:	
				visit((SimpleCompare)node, ctx);
				break;
			case GTEQ_OPERATOR:
				visit((SimpleCompare)node, ctx);
				break;
			case BETWEEN_OPERATOR:	
				visit((BetweenCompare)node, ctx);
				break;
			case IN_OPERATOR:	
				visit((InCompare)node, ctx);
				break;
			case IS_NOT_NULL_OPERATOR:	
				visit((NullCompare)node, ctx);
				break;
			case IS_NULL_OPERATOR:	
				visit((NullCompare)node, ctx);
				break;
			default:
				throw new IllegalArgumentException("Unexpected node kind '" + node.getKind() + "'");
		}		
	}

	@Override
	public void visit(SelectStatement select, Context ctx) {
		underlying.visit(select.getSelectClause(), ctx);
		underlying.visit(select.getFromClause(), ctx);	
		if(select.hasJoinClause()) {
			underlying.visit(select.getJoinClause(), ctx);
		}
		if(select.hasWhereClause()) {
			underlying.visit(select.getWhereClause(), ctx);
		}
		if(select.hasOrderByClause()) {
			underlying.visit(select.getJoinClause(), ctx);
		}
		if(select.hasLimitClause()) {
			underlying.visit(select.getLimitClause(), ctx);
		}
		if(select.hasGroupByClause()) {
			underlying.visit(select.getGroupByClause(), ctx);
		}
		if(select.hasHavingClause()) {
			underlying.visit(select.getHavingClause(), ctx);
		}
	}
	
	@Override
	public void visit(SelectClause select, Context ctx) {
		for(Node node : select.getColumns()) {
			switch (node.getKind()) {
				case FIELD_REF:
					underlying.visit((FieldRef)node, ctx);
					break;
				case FUNCTION_CALL:
					underlying.visit((FunctionCall)node, ctx);
					break;
				case STAR:
					underlying.visit((Star)node, ctx);	
					break;
				default:
					throw new IllegalArgumentException("Unexpected node type '" + node.getKind() + "'");
			}
		}
	}

	@Override
	public void visit(FromClause from, Context ctx) {
		if(order == Order.PARENT_FIRST) {		
			underlying.visit(from, ctx);
		}
		if(from.isSubSelect()) {
			underlying.visit(from.getSubSelect(), ctx.cloneMe());
		} else {
			underlying.visit(from.getTable(), ctx);
		}
		if(order == Order.CHILDREN_FIRST) {
			underlying.visit(from, ctx);
		}
	}
	
	@Override
	public void visit(JoinClause joinClause, Context ctx) {
		for(Join join : joinClause.getJoins()) {
			underlying.visit(join, ctx);
		}
	}

	@Override
	public void visit(Join join, Context ctx) {
		if(order == Order.PARENT_FIRST) {		
			underlying.visit(join, ctx);
		}
		if(join.isSubSelect()) {
			underlying.visit(join.getSubSelect(), ctx.cloneMe());
		} else {
			underlying.visit(join.getTable(), ctx);
		}
		underlying.visit(join.getCondition(), ctx);
		if(order == Order.CHILDREN_FIRST) {
			underlying.visit(join, ctx);
		}
	}

	@Override
	public void visit(WhereClause where, Context ctx) {
		ctx.enterWhereScope();
		visit(where.getExpression(), ctx);
		ctx.leaveWhereScope();
	}
	
	@Override
	public void visit(GroupByClause group, Context ctx) {
		if(order == Order.PARENT_FIRST) {		
			underlying.visit(group, ctx);
		}
		for(FieldRef fieldRef : group.getFields()) {
			underlying.visit(fieldRef, ctx);
		}
		if(order == Order.CHILDREN_FIRST) {
			underlying.visit(group, ctx);
		}
	}

	@Override
	public void visit(HavingClause having, Context ctx) {
		ctx.enterHavingScope();
		visit(having.getExpression(), ctx);
		ctx.leaveHavingScope();
	}

	@Override
	public void visit(OrderByClause order, Context ctx) {
		if(this.order == Order.PARENT_FIRST) {		
			underlying.visit(order, ctx);
		}
		for(FieldRef fieldRef : order.getFields()) {
			underlying.visit(fieldRef, ctx);
		}
		if(this.order == Order.CHILDREN_FIRST) {
			underlying.visit(order, ctx);
		}
	}

	@Override
	public void visit(LimitClause limit, Context ctx) {
		underlying.visit(limit, ctx);
	}
	
	@Override
	public void visit(BoolExpression expression, Context ctx) {
		if(expression instanceof CompositeBoolExpression) {
			for(BoolExpression child : ((CompositeBoolExpression)expression).getChildren()) {
				visit(child, ctx);
			}
		}
	}

	@Override
	public void visit(Compare compare, Context ctx) {
		underlying.visit(compare.getFieldRef(), ctx);
		if(compare instanceof BetweenCompare) {
			underlying.visit((BetweenCompare) compare, ctx);
		} else if(compare instanceof InCompare) {
			underlying.visit((InCompare) compare, ctx);
		} else if(compare instanceof NullCompare) {
			underlying.visit((NullCompare) compare, ctx);
		} else {
			visit((SimpleCompare) compare, ctx);
		}
	}

	@Override
	public void visit(SimpleCompare compare, Context ctx) {
		underlying.visit(compare.getFieldRef(), ctx);
	}

	@Override
	public void visit(InCompare in, Context ctx) {
		underlying.visit(in.getFieldRef(), ctx);
		if(in.isConstant()) {
			underlying.visit(in.getSet(), ctx);
		} else {			
			if(scope == Scope.MULTI_LEVEL) {
				underlying.visit(in.getSelect(), ctx);
			}
		}
	}

	@Override
	public void visit(BetweenCompare between, Context ctx) {
		if(kind == Kind.BETWEEN_OPERATOR) {			
			underlying.visit(between.getRange(), ctx);
		}
	}
	
	@Override
	public void visit(NullCompare nullCompare, Context ctx) {
		if(kind == Kind.IS_NULL_OPERATOR || kind == Kind.IS_NOT_NULL_OPERATOR) {			
			underlying.visit(nullCompare, ctx);
		}
	}

	@Override
	public void visit(FieldRef fieldRef, Context ctx) {
		if(kind == Kind.FIELD_REF) {			
			underlying.visit(fieldRef, ctx);
		}
	}
	
	@Override
	public void visit(EntityRef entityRef, Context ctx) {
		if(kind == Kind.ENTITY_REF) {			
			underlying.visit(entityRef, ctx);
		}
	}

	@Override
	public void visit(FunctionCall call, Context ctx) {
		if(order == Order.PARENT_FIRST) {		
			underlying.visit(call, ctx);
		}
		for(Node node : call.getParameters()) {
			if(node instanceof FieldRef) {
				underlying.visit((FieldRef)node, ctx);				
			} else {
				underlying.visit((Star)node, ctx);
			}
		}
		if(order == Order.CHILDREN_FIRST) {
			underlying.visit(call, ctx);
		}
	}

	@Override
	public void visit(Star star, Context ctx) {
		if(kind == Node.Kind.STAR) {			
			underlying.visit(star, ctx);
		}
	}

	@Override
	public void visit(Constant constant, Context ctx) {
		if(kind == Kind.CONSTANT) {			
			underlying.visit(constant, ctx);
		}
	}

	@Override
	public void visit(ConstantRange range, Context ctx) {
		if(kind == Kind.CONSTANT_RANGE) {			
			underlying.visit(range, ctx);
		}
	}

	@Override
	public void visit(ConstantSet set, Context ctx) {
		if(kind == Kind.CONSTANT_SET) {			
			underlying.visit(set, ctx);
		}
	}
}
