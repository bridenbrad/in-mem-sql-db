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
import com.tirion.db.sql.ast.drop.DropStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DropTransformer extends AbstractTransformer {

	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.DROP_STATEMENT);
		
		String tableName = ((CommonTree) root.getChild(0)).getText();
//		int partitionNumber = Integer.MIN_VALUE;
//		if(root.getChildCount() > 1) {
//			partitionNumber = Integer.parseInt(((CommonTree) root.getChild(1)).getText());
//		}
		
		DropStatement stmt = new DropStatement();
		stmt.setTableName(tableName);
//		stmt.setPartitionNumber(partitionNumber);
		
		return stmt;
	}
}
