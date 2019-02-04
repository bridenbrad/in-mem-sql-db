/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common.constant;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.sql.ast.common.Enriched;

/**
 * Single constant value, such as in 'x = 5'.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Constant extends BaseConstant {

	private static final long serialVersionUID = 6348598385821272418L;
	
	@Enriched // string will be replaced with value of correct type
	@JsonProperty
	private Object value;
	
	public Constant() {
	}
	
	public Constant(Object value) {
		this.value = value;
	}

	public <T> T getValue(Class<T> clazz) {
		return clazz.cast(value);
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public Kind getKind() {
		return Kind.CONSTANT;
	}

	@Override
	public String toSql() {
		return value.toString();
	}
}
