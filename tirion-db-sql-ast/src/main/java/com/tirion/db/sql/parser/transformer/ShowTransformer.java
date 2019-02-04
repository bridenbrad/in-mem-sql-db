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
import com.tirion.db.sql.ast.show.ShowTablesStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ShowTransformer extends AbstractTransformer {

	@Override
	public Node transform(CommonTree root) {
		assertType(root, SqlGrammarParser.SHOW_STATEMENT);
		return new ShowTablesStatement();
	}
}
