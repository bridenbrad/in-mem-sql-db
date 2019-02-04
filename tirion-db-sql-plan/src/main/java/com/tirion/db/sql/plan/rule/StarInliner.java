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

import com.tirion.db.catalog.model.Field;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.Star;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.select.SelectClause;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Inlines x.* expressions in SELECT clause. It will update list
 * of nodes in {@link SelectClause}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class StarInliner extends AbstractVisitor {
	
	public StarInliner(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(SelectClause select, Context ctx) {
		List<Node> newColumns = new ArrayList<Node>();
		for(Node node : select.getColumns()) {
			if(!(node instanceof Star)) {
				newColumns.add(node);
				continue;
			}
			Star star = (Star) node;
			if(!ctx.hasEntity(star.getOwner())) {
				ctx.getListener().onMessage("Unknown table in x.* expression in SELECT clause: '" + star.getOwner() + "'", MessageKind.ERROR);
			}
			for(Field field : ctx.getEntity(star.getOwner()).getFields()) {
				FieldRef fieldRef = new FieldRef(field.getName());
				fieldRef.setAlias(fieldRef.getName());
				fieldRef.setOwner(star.getOwner());
				fieldRef.setType(field.getDeclaredType());
				fieldRef.setParent(select);
				newColumns.add(fieldRef);
			}
		}
		select.setColumns(newColumns);
	}
}
