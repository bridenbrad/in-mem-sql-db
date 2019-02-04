/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.describe;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DescribeTableStatement extends AbstractNode {

	private static final long serialVersionUID = -2239239581424494723L;
	
	private String tableName;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public Kind getKind() {
		return Kind.DESCRIBE_TABLE_STATEMENT;
	}

	@Override
	public String toSql() {
		return "DESCRIBE TABLE " + getTableName();
	}
}
