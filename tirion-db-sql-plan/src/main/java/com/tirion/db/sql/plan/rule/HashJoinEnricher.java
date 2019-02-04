/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import com.tirion.db.common.JoinType;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.bool.And;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.compare.EqCompare;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Will tag {@link Join} in case hash based joining is possible.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class HashJoinEnricher extends AbstractVisitor {

	public HashJoinEnricher(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(Join join, Context ctx) {
		if(isHashJoinPossible(join, ctx)) {
			join.setHashCandidate(true);
		}
	}
	
	private boolean isHashJoinPossible(Join join, Context ctx) {
		if(join.getJoinType() != JoinType.INNER) {
			return false;
		}
		BoolExpression condition = join.getCondition();
		if(condition instanceof And) {
			for(BoolExpression expr : ((And)condition).getChildren()) {
				if(!(expr instanceof EqCompare)) {
					return false;
				}
				EqCompare compare = (EqCompare) expr;
				if(!(compare.getRight() instanceof FieldRef)) {
					return false; // hmm, remove this limitation?
				}
			}
			return true;
		} else if(condition instanceof EqCompare) {
			return true;
		} else {			
			return false;
		}
	}
}
