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
import com.tirion.db.sql.ast.set.SetStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SetTransformer extends AbstractTransformer {

	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.SET_STATEMENT);
		
		SetStatement stmt = new SetStatement();
		stmt.setName(Util.trimQuotes(root.getChild(0).getText()));
		stmt.setValue(Util.trimQuotes(root.getChild(1).getText()));
		
		return stmt;
	}

//	private String getNamespaceName(CommonTree root) {
//		StringBuilder buffer = new StringBuilder();
//		for (int i = 0; i < root.getChildCount(); i++) {
//			CommonTree child = (CommonTree) root.getChild(i);
//			if(child.getType() == SqlGrammarParser.EQ) {
//				break; // = sign
//			}
//			assertType(child, SqlGrammarParser.NAME);
//			
//			buffer.append(child.getText());
//			if(i < root.getChildCount() - 1) {
//				buffer.append('.');
//			}
//		}		
//		String result = buffer.toString();
//		if(result.charAt(result.length()-1) == '.') {
//			result = result.substring(0, result.length()-1);
//		}
//		return result;
//	}
//	
//	private String getValue(CommonTree root) {
//		for (int i = 0; i < root.getChildCount(); i++) {
//			CommonTree child = (CommonTree) root.getChild(i);
//			if(child.getType() != SqlGrammarParser.EQ) {
//				continue;
//			}
//			CommonTree value = (CommonTree) root.getChild(i+1);
//			return value.getText();
//		}
//		throw new IllegalStateException("No value in SET statement");
//	}
}
