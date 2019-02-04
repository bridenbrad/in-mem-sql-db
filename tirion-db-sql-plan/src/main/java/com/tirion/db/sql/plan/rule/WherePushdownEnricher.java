/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.bool.And;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.bool.Or;
import com.tirion.db.sql.ast.expression.compare.Compare;
import com.tirion.db.sql.ast.select.WhereClause;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class WherePushdownEnricher extends AbstractVisitor {
	
	public WherePushdownEnricher(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(WhereClause where, Context ctx) {
		Map<String, BoolExpression> relationToPushableExpression = calculateIfFiltersArePushable(where.getExpression(), ctx);
		if(relationToPushableExpression != null) {
			where.setFiltersPushable(true);
			where.setRelationToPushableExpression(relationToPushableExpression);
		}
	}
	
	private Map<String, BoolExpression> calculateIfFiltersArePushable(BoolExpression expression, Context ctx) {
		Map<String, BoolExpression> relationToPushableExpression = new HashMap<String, BoolExpression>();
		if(expression instanceof Or) {
			return null;
		}
		if(expression instanceof Compare) {
			Compare compare = (Compare) expression;
			relationToPushableExpression.put(compare.getFieldRef().getOwner(), expression);
			return relationToPushableExpression;
		}
		And and = (And) expression;
		for(BoolExpression child : and.getChildren()) {
			String relationName = refersToSingleRelation(child.getAllFieldRefs());
			if(relationName == null) {
				return null;
			}
			relationToPushableExpression.put(relationName, child);
		}
		return relationToPushableExpression;
	}
	
	private String refersToSingleRelation(List<FieldRef> fieldRefs)  {
		Set<String> relations = new HashSet<String>();
		for(FieldRef fieldRef : fieldRefs) {
			relations.add(fieldRef.getOwner());
		}
		if(relations.size() > 1) {
			return null;
		}
		return relations.iterator().next();
	}
}
