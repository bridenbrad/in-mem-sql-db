/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.expression.bool;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.set.SimpleSmartSet;
import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.expression.compare.InCompare;

/**
 * Root of AND and OR.
 */
public abstract class CompositeBoolExpression extends BoolExpression {

	private static final long serialVersionUID = 3854199867871801392L;
	
	@JsonProperty
	private final List<BoolExpression> children;
	
	public CompositeBoolExpression() {
		super();
		children = new ArrayList<BoolExpression>();
	}
		
	@Override
	public List<InCompare> getSubSelects() {
		List<InCompare> result = new ArrayList<InCompare>();
		for(BoolExpression child : children) {			
			result.addAll(child.getSubSelects());
		}
		return result;
	}
	
	@Override
	public List<FieldRef> getAllFieldRefs() {
		List<FieldRef> list = new ArrayList<FieldRef>();
		for(BoolExpression child : children) {
			list.addAll(child.getAllFieldRefs());
		}
		return list;
	}

	@Override
	public final SortedSet<String> getTableNames() {
		SortedSet<String> set = new TreeSet<String>();
		for(BoolExpression child : children) {
			set.addAll(child.getTableNames());
		}
		return set;
	}

	@Override
	public final SmartSet<FieldRef> getRequiredFieldRefs() {
		SmartSet<FieldRef> set = new SimpleSmartSet<FieldRef>();
		for(BoolExpression child : children) {
			set.add(child.getRequiredFieldRefs());
		}
		return set;
	}

	public final List<BoolExpression> getChildren() {
		return children;
	}

	public final void append(BoolExpression node) {
		children.add(node);
		node.setParent(this);
	}
}
