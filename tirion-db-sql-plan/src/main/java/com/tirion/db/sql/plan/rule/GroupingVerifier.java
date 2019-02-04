/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import java.util.List;

import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.Node.Kind;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Checks that GROUP BY & SELECT clauses are consistent i.e.
 * have same number of grouping columns in proper order etc.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GroupingVerifier extends AbstractVisitor {
	
	public GroupingVerifier(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(SelectStatement select, Context ctx) {
		if(!select.hasGroupByClause()) {
			return;
		}
		
		List<Node> selectColumns = select.getSelectClause().getColumns();
		List<FieldRef> groupingColumns = select.getGroupByClause().getFields();
		
		if(groupingColumns.size() > selectColumns.size()) {
			ctx.getListener().onMessage("Grouping column count mismatch in GROUP BY & SELECT clause", MessageKind.ERROR);
		}
		for (int i = 0; i < groupingColumns.size(); i++) {
			FieldRef grouping = groupingColumns.get(i);
			Node node = selectColumns.get(i);
			if(node.getKind() != Kind.FIELD_REF) {
				ctx.getListener().onMessage("Mismatch in GROUP BY & SELECT grouping fields: '" + node.toSql() + "'", MessageKind.ERROR);
			}
			FieldRef fieldRef = (FieldRef) node;
			if(!grouping.areSame(fieldRef)) {
				ctx.getListener().onMessage("Mismatch in GROUP BY & SELECT grouping fields: '" + fieldRef.toSql() + "'", MessageKind.ERROR);
			}
		}
	}
}
