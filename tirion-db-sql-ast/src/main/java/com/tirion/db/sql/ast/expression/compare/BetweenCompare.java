/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.compare;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.ast.common.constant.ConstantRange;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BetweenCompare extends Compare {

	private static final long serialVersionUID = -3436613176997420145L;

	@JsonProperty
	private ConstantRange range;

	public ConstantRange getRange() {
		return range;
	}

	public void setRange(ConstantRange range) {
		this.range = range;
		this.range.setParent(this);
	}

	@Override
	public Kind getKind() {
		return Kind.BETWEEN_OPERATOR;
	}

	@Override
	public String toSql() {
		return getFieldRef().toSql() + " BETWEEN " + range.toSql();
	}
}
