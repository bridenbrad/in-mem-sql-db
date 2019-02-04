/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.parser.transformer;

import org.antlr.runtime.tree.CommonTree;

import com.tirion.common.Util;
import com.tirion.db.sql.SqlGrammarParser;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.load.LoadStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LoadTransformer extends AbstractTransformer {

	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.LOAD_STATEMENT);
		
		String tableName = ((CommonTree)root.getChild(0)).getText();
		String fileName = ((CommonTree)root.getChild(1)).getText();
//		String separator = ((CommonTree)root.getChild(2)).getText();
		
		LoadStatement stmt = new LoadStatement();
		stmt.setTableName(tableName);
		stmt.setFileNames(Util.trimQuotes(fileName));
//		stmt.setSeparator(Util.trimQuotes(separator));
		
//		if(root.getChildCount() > 3) { 
//			// has PARTITION BY or ON HOST clause
//			CommonTree child = (CommonTree)root.getChild(3);
//			assertTypes(child, SqlGrammarParser.PARTITION, SqlGrammarParser.WORKER);
//			
//			if(child.getType() == SqlGrammarParser.PARTITION) {				
//				for (int i = 0; i < child.getChildCount(); i++) {
//					stmt.appendPartitionBy(((CommonTree)child.getChild(i)).getText());
//				}
//			} else {
//				String host = ((CommonTree)child.getChild(0)).getText();
//				stmt.setWorkerName(Util.trimQuotes(host));
//			}
//		}
		return stmt;
	}
}
