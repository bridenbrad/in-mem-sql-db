/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.delete;

import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DeleteStatement extends AbstractNode {

	private static final long serialVersionUID = 3784453970355768233L;
	
	private String tableName;
	private BoolExpression filter;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public BoolExpression getFilter() {
		return filter;
	}

	public void setFilter(BoolExpression filter) {
		this.filter = filter;
	}

	@Override
	public Kind getKind() {
		return Kind.DELETE_STATEMENT;
	}

	@Override
	public String toSql() {
		return "DELETE FROM TABLE " + getTableName() + " WHERE " + filter.toSql();
	}
}
