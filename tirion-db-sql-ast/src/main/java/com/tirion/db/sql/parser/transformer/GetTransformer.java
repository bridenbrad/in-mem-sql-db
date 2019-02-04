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
import com.tirion.db.sql.ast.get.GetStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GetTransformer extends AbstractTransformer {

	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.GET_STATEMENT);
		
		GetStatement stmt = new GetStatement();
		stmt.setName(Util.trimQuotes(root.getChild(0).getText()));
		
		return stmt;
	}
	
//	private String getNamespaceName(CommonTree root) {
//		StringBuilder buffer = new StringBuilder();
//		for (int i = 0; i < root.getChildCount(); i++) {
//			CommonTree child = (CommonTree) root.getChild(i);
//			assertType(child, SqlGrammarParser.NAME);
//			buffer.append(child.getText());
//			if(i < root.getChildCount() - 1) {
//				buffer.append('.');
//			}
//		}		
//		return buffer.toString();
//	}
}
