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
import com.tirion.db.sql.ast.select.LimitClause;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LimitVerifier extends AbstractVisitor {

	private static final int MIN_VALUE = 1;
	private static final int MAX_VALUE = Integer.MAX_VALUE;

	public LimitVerifier(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(LimitClause limit, Context ctx) {
		int value = limit.getValue();
		if(value < MIN_VALUE || value > MAX_VALUE) {
			ctx.getListener().onMessage("Limit value not in valid range", MessageKind.ERROR);
		}
	}
}
