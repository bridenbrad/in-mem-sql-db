/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.compare;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.set.SimpleSmartSet;
import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class Compare extends BoolExpression {
	
	private static final long serialVersionUID = 1679860158485452649L;
	
	@JsonProperty
	private FieldRef fieldRef;

	/**
	 * Returns LHS of compare.
	 */
	public final FieldRef getFieldRef() {
		return fieldRef;
	}

	/**
	 * Sets LHS of compare.
	 */
	public final void setFieldRef(FieldRef fieldRef) {
		this.fieldRef = fieldRef;
		fieldRef.setParent(this);
	}

	/**
	 * By default returns list containing LHS' {@link FieldRef}.
	 */
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		set.add(fieldRef);
		return set;
	}

	/**
	 * Returns empty set by default.
	 */
	@Override
	public SortedSet<String> getTableNames() {
		return new TreeSet<String>();
	}

	@Override
	public List<InCompare> getSubSelects() {
		return new ArrayList<InCompare>();
	}

	@Override
	public List<FieldRef> getAllFieldRefs() {
		List<FieldRef> list = new ArrayList<FieldRef>(1);
		list.add(getFieldRef());
		return list;
	}
}
