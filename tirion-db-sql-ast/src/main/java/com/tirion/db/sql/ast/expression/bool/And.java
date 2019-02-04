/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.bool;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class And extends CompositeBoolExpression {
	
	private static final long serialVersionUID = -8459634626771644L;

	@Override
	public Kind getKind() {
		return Kind.AND_EXPRESSION;
	}

	@Override
	public String toSql() {
		String result = "(";
		for (int i = 0; i < getChildren().size(); i++) {
			result += getChildren().get(i).toSql();
			if(i < getChildren().size() - 1) {
				result += " AND ";
			}
		}
		return result + ")";
	}
}
