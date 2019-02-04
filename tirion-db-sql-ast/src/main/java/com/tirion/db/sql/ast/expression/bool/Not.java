/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.bool;

import java.util.List;
import java.util.SortedSet;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.expression.compare.InCompare;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Not extends BoolExpression {

	private static final long serialVersionUID = 8268020526731483877L;
	
	@Override
	public List<InCompare> getSubSelects() {
		throw new NotYetImplementedException();
	}
	
	@Override
	public List<FieldRef> getAllFieldRefs() {
		throw new NotYetImplementedException();
	}

	@Override
	public SortedSet<String> getTableNames() {
		throw new NotYetImplementedException();
	}

	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		throw new NotYetImplementedException();
	}

	@Override
	public Kind getKind() {
//		return Kind.NOT_EXPRESSION;
		throw new NotYetImplementedException();
	}

	@Override
	public String toSql() {
		throw new NotYetImplementedException();
	}
}
