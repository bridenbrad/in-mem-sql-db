/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message;
import com.tirion.db.sql.ast.select.GroupByClause;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Checks that number of grouping columns is less than predefined
 * constant.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GroupByVerifier extends AbstractVisitor {

	private static final int MAX_FIELDS = 16;

	public GroupByVerifier(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(GroupByClause group, Context ctx) {
		if(group.getFields().size() > MAX_FIELDS) {
			ctx.getListener().onMessage("Maximum number of fields in GROUP BY is " + MAX_FIELDS, Message.MessageKind.ERROR);	
		}
	}
}
