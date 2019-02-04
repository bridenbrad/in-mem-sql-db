/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common.constant;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.Util;
import com.tirion.common.date.DateUtil;
import com.tirion.common.type.Type;
import com.tirion.db.sql.ast.common.Enriched;
import com.tirion.db.sql.ast.common.Value;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BaseConstant extends Value {
	
	private static final long serialVersionUID = -6454001531074907560L;
	
	@Enriched
	@JsonProperty
	private Type type;

	@Override
	public final Type getType() {
		return type;
	}
	
	public final void setType(Type type) {
		this.type = type;
	}
	
	public static final Object parseFromString(Object o, Type type, Runtime runtime) {
		final String str = (String)o;
		switch (type) {
			case BOOLEAN:
				return Boolean.parseBoolean(str);
			case BYTE:
				return Byte.parseByte(str);
			case SHORT:
				return Short.parseShort(str);
			case INT:
				return Integer.parseInt(str);
			case LONG:
				return Long.parseLong(str);
			case FLOAT:
				return Float.parseFloat(str);
			case DOUBLE:
				return Double.parseDouble(str);
			case VARCHAR:
				throw new NotYetImplementedException();
//				return runtime.pool().getToken(Util.trimQuotes(str));
			case TIME:
				return DateUtil.parseTime(Util.trimQuotes(str));
			case DATE:
				return DateUtil.parseDateNumber(Util.trimQuotes(str));
			case TIMESTAMP:
				return DateUtil.parseTimestamp(Util.trimQuotes(str));
			default:
				throw new NotYetImplementedException(type.toString());
		}
	}
}
