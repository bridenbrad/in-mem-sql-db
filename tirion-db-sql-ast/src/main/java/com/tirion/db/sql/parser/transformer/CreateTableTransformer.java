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
import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.EntityType;
import com.tirion.db.catalog.model.SimpleEntity;
import com.tirion.db.catalog.model.SimpleField;
import com.tirion.db.catalog.model.options.Options;
import com.tirion.db.sql.SqlGrammarParser;
import com.tirion.db.sql.ast.Node;
import com.tirion.db.sql.ast.create.CreateStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CreateTableTransformer extends AbstractTransformer {

	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.CREATE_STATEMENT);
			
		String tableName = ((CommonTree) root.getChild(0)).getText();
		
		EntityType entityType = EntityType.LARGE;		
		CommonTree last = (CommonTree) root.getChild(root.getChildCount() - 1);
		if(last.getType() == SqlGrammarParser.AS) {
			entityType = EntityType.parseFromString(((CommonTree)last.getChild(0)).getText());
		}	
		
		SimpleEntity entityInfo = new SimpleEntity(tableName, entityType);
		
		int fieldIndex = 0;
		for (int i = 1; i < root.getChildCount(); i++) {
			CommonTree child = (CommonTree) root.getChild(i);
			if(child.getType() == SqlGrammarParser.AS) {
				break;
			}
			String columnName = child.getText();
			String columnType = child.getChild(0).getText();
			boolean nullable = true;
			Options options = null;
			if(child.getChildCount() > 1) { // has nullability & options
				int index = 1;
				while(index < child.getChildCount()) {
					CommonTree childChild = (CommonTree) child.getChild(index);
					if(childChild.getType() == SqlGrammarParser.NULL) {
						if(childChild.getChildCount() > 0) {
							nullable = false;
						}						
					} else {
						assertType(childChild, SqlGrammarParser.STRING);
						options = Options.buildFrom(nullable, Util.trimQuotes(childChild.getText()));
					}
					++index;
				}
			}
			SimpleField fieldInfo = new SimpleField(columnName, entityInfo.getName(), 
					Type.parseFromString(columnType), fieldIndex, options);
			entityInfo.append(fieldInfo);
			++fieldIndex;
		}
		
		CreateStatement stmt = new CreateStatement();
		stmt.setEntity(entityInfo);
		return stmt;
	}
}
