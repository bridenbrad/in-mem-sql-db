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
import com.tirion.db.sql.ast.select.OrderByClause;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class OrderByVerifier extends AbstractVisitor {

	private static final int MAX_FIELDS = 16;
	
	public OrderByVerifier(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(OrderByClause order, Context ctx) {
		if(order.getOrderFieldCount() > MAX_FIELDS) {
			ctx.getListener().onMessage("Maximum number of fields in ORDER BY is " + MAX_FIELDS, Message.MessageKind.ERROR);	
		}
	}
}
