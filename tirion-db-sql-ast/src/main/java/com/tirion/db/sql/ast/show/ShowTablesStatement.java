/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.show;

import com.tirion.db.sql.ast.AbstractNode;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ShowTablesStatement extends AbstractNode {

	private static final long serialVersionUID = 8287452527137469448L;

	@Override
	public Kind getKind() {
		return Kind.SHOW_TABLES_STATEMENT;
	}

	@Override
	public String toSql() {
		return "SHOW TABLES";
	}
}
