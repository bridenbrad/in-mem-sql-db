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
import com.tirion.db.sql.ast.delete.DeleteStatement;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DeleteTransformer extends AbstractTransformer {

	private Transformer boolExpressionTransformer = new BooleanExpressionTransformer();
	
	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.DELETE_STATEMENT);
		
		String tableName = ((CommonTree) root.getChild(0)).getText();
		BoolExpression expression = (BoolExpression) boolExpressionTransformer.transform((CommonTree)root.getChild(1).getChild(0));
		
		DeleteStatement stmt = new DeleteStatement();
		stmt.setTableName(tableName);
		stmt.setFilter(expression);
		
		return stmt;
	}
}
