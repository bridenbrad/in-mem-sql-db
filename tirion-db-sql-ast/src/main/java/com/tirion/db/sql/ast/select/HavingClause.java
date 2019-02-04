/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.Fields;
import com.tirion.db.sql.ast.common.Enriched;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FieldRefsAware;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class HavingClause extends AbstractNode implements FieldRefsAware {
	
	private static final long serialVersionUID = 8416386298335802487L;

	@JsonProperty
	private BoolExpression expression;
	
	@Enriched
	@JsonProperty
	private Fields input;
	
	public Fields getInput() {
		return input;
	}

	public void setInput(Fields input) {
		this.input = input;
	}

	public BoolExpression getExpression() {
		return expression;
	}

	public void setExpression(BoolExpression expression) {
		this.expression = expression;
		this.expression.setParent(this);
	}
	
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		return expression.getRequiredFieldRefs();
	}

	@Override
	public Kind getKind() {
		return Kind.HAVING_CLAUSE;
	}

	@Override
	public String toSql() {
		return "HAVING " + expression.toSql();
	}
}
