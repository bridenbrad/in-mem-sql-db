/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import java.util.List;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.SimpleEntity;
import com.tirion.db.catalog.model.SimpleField;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.Node.Kind;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FunctionCall;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.select.SelectClause;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Applied once per SELECT statement. Does not recurse into sub-selects.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SelectStatementVisitor extends AbstractVisitor {

	private final EntityCollector entityCollector;
	private final StarInliner starInliner;
	private final FieldEnricher fieldEnricher;
	private final FunctionEnricher functionEnricher;
	private final BoolExpressionEnricher boolExpressionEnricher;
	private final WherePushdownEnricher wherePushdownEnricher;
	private final JoinClauseVerifier joinClauseVerifier;
	private final HashJoinEnricher hashJoinEnricher;
	private final GroupByVerifier groupByVerifier;
	private final GroupingVerifier groupingVerifier;
	private final OrderByVerifier orderByVerifier;
	private final LimitVerifier limitVerifier;
	
	public SelectStatementVisitor(Runtime runtime) {
		super(runtime);
		entityCollector = new EntityCollector(runtime);
		starInliner = new StarInliner(runtime);
		fieldEnricher = new FieldEnricher(runtime);
		functionEnricher = new FunctionEnricher(runtime);
		boolExpressionEnricher = new BoolExpressionEnricher(runtime);
		wherePushdownEnricher = new WherePushdownEnricher(runtime);
		joinClauseVerifier = new JoinClauseVerifier(runtime);
		hashJoinEnricher = new HashJoinEnricher(runtime);
		groupByVerifier = new GroupByVerifier(runtime);
		groupingVerifier = new GroupingVerifier(runtime);
		orderByVerifier = new OrderByVerifier(runtime);
		limitVerifier = new LimitVerifier(runtime);		
	}

	@Override
	public void visit(SelectStatement select, Context ctx) {
		if(select.hasGroupByClause() != select.getSelectClause().hasGroupingFunction()) {
			ctx.getListener().onMessage("Grouping function requires GROUP BY clause & vice versa", MessageKind.ERROR);
		}
		
		if(select.hasLimitClause()) {			
			limitVerifier.visit(select.getLimitClause(), ctx);
		}
		
		{			
			entityCollector.visit(select, ctx);
			ctx.detach();
			for(Entity entity : entityCollector.getEntities()) {
				ctx.register(entity);
			}
		}
		
		// inline x.*
		starInliner.visit(select.getSelectClause(), ctx);

		// visit all field references in SELECT clause
		visitFieldRefs(select.getSelectClause().getAllFieldRefs(), ctx);
		
		if(select.hasWhereClause()) {
			{
				visitFieldRefs(select.getWhereClause().getExpression().getAllFieldRefs(), ctx);
			}
			{				
				ctx.enterWhereScope();
				boolExpressionEnricher.visit(select.getWhereClause().getExpression(), ctx);
				ctx.leaveWhereScope();
			}
			{				
				ctx.enterWhereScope();
				wherePushdownEnricher.visit(select.getWhereClause(), ctx);
				ctx.leaveWhereScope();
			}
		}
		
		if(select.hasJoinClause()) {
			{				
				joinClauseVerifier.visit(select.getJoinClause(), ctx);
			}
			{				
				Context newCtx = ctx.cloneMe();
				newCtx.register(ctx.getEntity(select.getFromClause().getAlias()));
				for(Join join : select.getJoinClause().getJoins()) {
					newCtx.register(ctx.getEntity(join.getAlias()));
					visitFieldRefs(join.getCondition().getAllFieldRefs(), newCtx);
					newCtx.enterJoinScope();
					boolExpressionEnricher.visit(join.getCondition(), newCtx);
					newCtx.leaveJoinScope();
				}
			}
			{				
				for(Join join : select.getJoinClause().getJoins()) {
					hashJoinEnricher.visit(join, ctx);
				}
			}
		}
		
		if(select.hasGroupByClause()) {
			{
				visitFieldRefs(select.getGroupByClause().getFields(), ctx);
			}
			{
				for(FunctionCall call : select.getSelectClause().getFunctionCalls()) {
					functionEnricher.visit(call, ctx);
				}				
			}
			{				
				groupByVerifier.visit(select.getGroupByClause(), ctx);
			}
			{				
				groupingVerifier.visit(select, ctx);		
			}
		}
		
		if(select.hasGroupByClause()) {
			Context newCtx = ctx.cloneMe();
			newCtx.detach();
			for(FieldRef fieldRef : select.getGroupByClause().getFields()) {				
				newCtx.register(new SimpleField(fieldRef.getName(), fieldRef.getOwner(), fieldRef.getType(), Integer.MIN_VALUE));
			}
			for(FunctionCall call : select.getSelectClause().getFunctionCalls()) {
				newCtx.register(new SimpleField(call.getAlias(), call.getOwner(), call.getReturnType(), Integer.MIN_VALUE));
			}
			ctx = newCtx;
		}
		if(select.hasHavingClause()) {				
			visitFieldRefs(select.getHavingClause().getExpression().getAllFieldRefs(), ctx);
			ctx.enterHavingScope();
			boolExpressionEnricher.visit(select.getHavingClause().getExpression(), ctx);
			ctx.leaveHavingScope();
		}
		
		if(select.hasOrderByClause()) {
			visitFieldRefs(select.getOrderByClause().getFields(), ctx);
			orderByVerifier.visit(select.getOrderByClause(), ctx);
		}
		
		select.setOutput(buildSelectClauseOutput(select.getSelectClause(), ctx));
	}
	
	private Entity buildSelectClauseOutput(SelectClause selectClause, Context ctx) {
		int fieldIndex = 0;
		SimpleEntity entity = new SimpleEntity(ctx.nextUniqueEntityName());		
		for(Node node : selectClause.getColumns()) {
			if(node.getKind() == Kind.FIELD_REF) {
				FieldRef fieldRef = (FieldRef) node;
				entity.append(ctx.getField(fieldRef.getOwner(), fieldRef.getName()));
			} else {
				FunctionCall functionCall = (FunctionCall) node;
				entity.append(new SimpleField(functionCall.getAlias(), entity.getName(), functionCall.getReturnType(), fieldIndex));
			}
			++fieldIndex;
		}
		return entity;
	}
	
	private void visitFieldRefs(List<FieldRef> fieldRefs, Context ctx) {
		for (FieldRef fieldRef : fieldRefs) {
			fieldEnricher.visit(fieldRef, ctx);
		}
	}
}
