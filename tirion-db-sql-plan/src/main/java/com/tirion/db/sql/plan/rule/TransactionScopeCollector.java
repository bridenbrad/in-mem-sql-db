/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import java.util.HashSet;
import java.util.Set;

import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.select.FromClause;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Builds set of all physical tables touched by query so that we
 * can create transaction before semantic analysis. Will report
 * early error in case physical table does not exist.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TransactionScopeCollector extends AbstractVisitor {
	
	private final Set<String> tableNames = new HashSet<String>();

	public TransactionScopeCollector(Runtime runtime) {
		super(runtime);
	}

	public Set<String> getTableNames() {
		return tableNames;
	}

	@Override
	public void visit(FromClause from, Context ctx) {
		if(from.isTable()) {
			ensureExists(from.getTableName(), ctx);
			tableNames.add(from.getTableName());
		}
	}

	@Override
	public void visit(Join join, Context ctx) {
		if(join.isTable()) {
			ensureExists(join.getTableName(), ctx);
			tableNames.add(join.getTableName());
		}
	}
	
	private void ensureExists(String tableName, Context ctx) {
		if(!ctx.hasEntity(tableName)) {
			ctx.getListener().onMessage("Non-existent table '" + tableName + "'", MessageKind.ERROR);
		}
	}
}
