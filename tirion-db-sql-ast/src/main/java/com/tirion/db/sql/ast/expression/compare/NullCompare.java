/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.compare;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class NullCompare extends Compare {
	
	private static final long serialVersionUID = -3451592416871368348L;

	public static final class IsNullCompare extends NullCompare {

		private static final long serialVersionUID = -8532667413344269212L;

		@Override
		public Kind getKind() {
			return Kind.IS_NULL_OPERATOR;
		}

		@Override
		public String toSql() {
			return getFieldRef().toSql() + " IS NULL";
		}
	}
	
	public static final class IsNotNullCompare extends NullCompare {

		private static final long serialVersionUID = -9011701909337683099L;

		@Override
		public Kind getKind() {
			return Kind.IS_NOT_NULL_OPERATOR;
		}

		@Override
		public String toSql() {
			return getFieldRef().toSql() + " IS NOT NULL";
		}
	}
}
