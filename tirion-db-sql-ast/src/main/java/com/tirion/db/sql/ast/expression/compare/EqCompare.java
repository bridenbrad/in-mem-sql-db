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
public final class EqCompare extends SimpleCompare {

	private static final long serialVersionUID = -7002546132822134470L;

	@Override
	public Kind getKind() {
		return Kind.EQ_OPERATOR;
	}

	@Override
	public String toSql() {
		return getFieldRef().toSql() + "=" + getRight().toSql();
	}
}
