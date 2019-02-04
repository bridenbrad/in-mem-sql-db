/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.expression.compare.InCompare;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Will invoke {@link SelectStatementVisitor} on all SELECTs, calling
 * it first on sub-selects.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class MainVisitor extends AbstractVisitor {
	
	public MainVisitor(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(SelectStatement select, Context ctx) {
		visitInternal(select, ctx);
	}
	
	private void visitInternal(SelectStatement select, Context ctx) {
		if(select.getFromClause().isSubSelect()) {
			visitInternal(select.getFromClause().getSubSelect(), ctx.cloneMe());
		}
		if(select.hasJoinClause()) {
			for(Join join : select.getJoinClause().getJoins()) {
				if(join.isSubSelect()) {
					visitInternal(join.getSubSelect(), ctx.cloneMe());
				}
			}
		}
		if(select.hasWhereClause()) {
			for(InCompare in : select.getWhereClause().getInSubSelects()) {				
				visitInternal(in.getSelect(), ctx.cloneMe());
			}
		}
		new SelectStatementVisitor(getRuntime()).visit(select, ctx);
		select.optimize(ctx);
	}
}
