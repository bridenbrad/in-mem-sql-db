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

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractTransformer implements Transformer {
	
	protected final void assertType(CommonTree node, int type) {
		if(node.getType() != type) {
			throw new IllegalArgumentException("Expected '" + tokenIdToString(type) + "' but found '" + tokenIdToString(node.getType()) + "'");
		}
	}
	
	protected final void assertTypes(CommonTree node, int...types) {
		for (int i = 0; i < types.length; i++) {
			if(node.getType() == types[i]) {
				return;
			}
		}
		throw new IllegalArgumentException("Expected '" + tokenIdsToString(types) + "' but found '" + tokenIdToString(node.getType()) + "'");
	}
	
	protected final String tokenIdToString(int tokenId) {
		return SqlGrammarParser.tokenNames[tokenId];
	}
	
	protected final String tokenIdsToString(int...tokenIds) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < tokenIds.length; i++) {
			buffer.append(SqlGrammarParser.tokenNames[tokenIds[i]]);
			buffer.append(',');
		}
		return buffer.toString();
	}
}
