/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.common.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.Util;
import com.tirion.db.sql.ast.common.Enriched;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ConstantSet extends BaseConstant {

	private static final long serialVersionUID = -3853722397013044361L;
	
	private final List<Object> strValues = new ArrayList<Object>();	
	@Enriched
	@JsonProperty
	private final SortedSet<Object> values = new TreeSet<Object>();

	/**
	 * Append string to set of string-based values.
	 */
	public void appendStr(Object value) {
		strValues.add(value);
	}
	
	/**
	 * Append casted value to set of typed values.
	 */
	public void append(Object value) {
		values.add(value);
	}
	
	public List<Object> getStrValues() {
		return strValues;
	}

	public <T> SortedSet<T> getValues(Class<T> clazz) {
		SortedSet<T> set = new TreeSet<T>();
		for(Object value : values) {
			set.add(clazz.cast(value));
		}
		return set;
	}
	
	/**
	 * Correct type will be returned.
	 */
	public SortedSet<Object> getSortedSet() {
		return values;
	}

	@Override
	public Kind getKind() {
		return Kind.CONSTANT_SET;
	}

	@Override
	public String toSql() {
		String result = "";
		for(Object value : strValues) {
			result += value;
			result += ",";
		}
		result = Util.trimCommasDots(result);
		return "(" + result + ")";
	}
}
