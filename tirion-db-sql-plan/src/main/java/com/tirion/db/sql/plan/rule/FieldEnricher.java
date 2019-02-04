/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FieldEnricher extends AbstractVisitor {
	
	public FieldEnricher(Runtime runtime) {
		super(runtime);
	}

	@Override
	public void visit(FieldRef fieldRef, Context ctx) {
		if(!fieldRef.hasAlias()) {
			fieldRef.setAlias(fieldRef.getName());
		}
		if(!fieldRef.hasOwner()) {
			fieldRef.setOwner(ctx.findOwnerFor(fieldRef.getName()));
		}
		if(!ctx.hasField(fieldRef.getOwner(), fieldRef.getName())) {
			ctx.getListener().onMessage("Unknown field '" + fieldRef.getOwner() + "." + fieldRef.getName() + "'", MessageKind.ERROR);
		}
		fieldRef.setType(ctx.getField(fieldRef.getOwner(), fieldRef.getName()).getDeclaredType());
	}
}
