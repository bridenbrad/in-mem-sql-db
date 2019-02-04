/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * All names (fields, entities etc) should be in lower case for consistency reasons.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Node extends Serializable {

	public static enum Kind {
		
		MERGE_STATEMENT,
		MATCHING_CLAUSE,
		THEN_CLAUSE,
		UPDATE_MATCH_ACTION,
		DELETE_MATCH_ACTION,
		INSERT_MATCH_ACTION,
		
		DROP_STATEMENT,
		
		CREATE_STATEMENT,
		
		LOAD_STATEMENT,
		
		DELETE_STATEMENT,
		INSERT_STATEMENT,
		
		TRUNCATE_STATEMENT,
		
		GET_STATEMENT,
		SET_STATEMENT,
		EXECUTE_STATEMENT,
		SHOW_TABLES_STATEMENT,
		DESCRIBE_TABLE_STATEMENT,
		
		SELECT_STATEMENT,
		SELECT_CLAUSE,
		WINDOW,
		PARTITION_BY,
		FRAMING,
		FROM_CLAUSE,
		JOIN_CLAUSE,
		/**
		 * Single join between 2 tables.
		 */
		JOIN,
		WHERE_CLAUSE,
		GROUP_BY_CLAUSE,
		HAVING_CLAUSE,
		ORDER_BY_CLAUSE,
		LIMIT_CLAUSE,
		
		/**
		 * Either table or event.
		 */
		ENTITY_REF,
		/**
		 * X = 5
		 */
		CONSTANT,
		/**
		 * X in (5, 6, 7, 8)
		 */
		CONSTANT_SET,
		/**
		 * X between 1 and 10
		 */
		CONSTANT_RANGE,
		/**
		 * Either column in table or field within event.
		 */
		FIELD_REF,
		FUNCTION_CALL,
		/**
		 * Used in count(*) scenarios.
		 */
		STAR, 
		
		AND_EXPRESSION,
		OR_EXPRESSION,
		NOT_EXPRESSION,
		
		EQ_OPERATOR,
		NEQ_OPERATOR,
		LT_OPERATOR,
		LTEQ_OPERATOR,
		GT_OPERATOR,
		GTEQ_OPERATOR,
		BETWEEN_OPERATOR,
		IN_OPERATOR,
		IS_NULL_OPERATOR,
		IS_NOT_NULL_OPERATOR,
		EXISTS_OPERATOR,
	}
	
	@JsonProperty
	Kind getKind();
	
	/**
	 * Convert AST into SQL. For development
	 * & testing purposes.
	 */
	String toSql();
}
