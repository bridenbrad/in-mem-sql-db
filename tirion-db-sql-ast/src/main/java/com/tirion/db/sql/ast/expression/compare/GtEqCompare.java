/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.compare;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GtEqCompare extends SimpleCompare {

	private static final long serialVersionUID = -3156334879994980446L;

	@Override
	public Kind getKind() {
		return Kind.GTEQ_OPERATOR;
	}

	@Override
	public String toSql() {
		return getFieldRef().toSql() + ">=" + getRight().toSql();
	}
}
