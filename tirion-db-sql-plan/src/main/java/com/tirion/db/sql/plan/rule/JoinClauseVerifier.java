/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.select.JoinClause;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Enforces maximum number of joins.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class JoinClauseVerifier extends AbstractVisitor {

	private static final int MAX_JOIN_COUNT = 8;
	
	public JoinClauseVerifier(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(JoinClause joinClause, Context ctx) {
		if(joinClause.getJoinCount() > MAX_JOIN_COUNT) {
			ctx.getListener().onMessage("Maximum number of joins is " + MAX_JOIN_COUNT, MessageKind.ERROR);
		}
	}
}
