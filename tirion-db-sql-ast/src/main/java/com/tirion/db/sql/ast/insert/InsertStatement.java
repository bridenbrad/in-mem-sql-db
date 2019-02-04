/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.insert;

import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.select.SelectStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InsertStatement extends AbstractNode {

	private static final long serialVersionUID = 7448427793796598152L;
	
	private String tableName;
	private SelectStatement select;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public SelectStatement getSelect() {
		return select;
	}

	public void setSelect(SelectStatement select) {
		this.select = select;
	}

	@Override
	public Kind getKind() {
		return Kind.INSERT_STATEMENT;
	}

	@Override
	public String toSql() {
		return "INSERT INTO TABLE " + getTableName() + " " + select.toSql();
	}
}
