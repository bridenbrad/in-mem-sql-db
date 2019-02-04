/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.parser.transformer;

import org.antlr.runtime.tree.CommonTree;

import com.tirion.db.sql.SqlGrammarParser;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.insert.InsertStatement;
import com.tirion.db.sql.ast.select.SelectStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InsertTransformer extends AbstractTransformer {

	private Transformer selectTransformer = new SelectTransformer();
	
	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.INSERT_STATEMENT);
		
		String tableName = ((CommonTree) root.getChild(0)).getText();
		SelectStatement select = (SelectStatement) selectTransformer.transform((CommonTree)root.getChild(1));
		
		InsertStatement stmt = new InsertStatement();
		stmt.setTableName(tableName);
		stmt.setSelect(select);
		
		return stmt;
	}
}
