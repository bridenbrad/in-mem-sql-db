/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LimitClause extends AbstractNode {
	
	private static final long serialVersionUID = -3740284798726193072L;
	
	@JsonProperty
	private int value;
	
	public LimitClause() {
		this(Integer.MIN_VALUE);
	}
	
	public LimitClause(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public Kind getKind() {
		return Kind.LIMIT_CLAUSE;
	}

	@Override
	public String toSql() {
		return "LIMIT " + getValue();
	}
}
