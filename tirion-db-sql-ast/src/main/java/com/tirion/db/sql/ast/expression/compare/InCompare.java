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

import com.tirion.db.sql.ast.common.constant.ConstantSet;
import com.tirion.db.sql.ast.select.SelectStatement;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InCompare extends Compare {
	
	private static final long serialVersionUID = 30081894159753854L;
	
	private boolean hasNot;
	
	// these two are mutually exclusive
	@JsonProperty
	private ConstantSet set;
	@JsonProperty
	private SelectStatement select;
	
//	private PhysicalOperator operator;
//	
//	/**
//	 * Returns physical operator that will produce set of values
//	 * that can be used then in parent query.
//	 */
//	public PhysicalOperator getOperator() {
//		return operator;
//	}
//
//	public void setOperator(PhysicalOperator operator) {
//		this.operator = operator;
//	}

	@Override
	public SortedSet<String> getTableNames() {
		SortedSet<String> set = new TreeSet<String>();
		if(!isConstant()) {
			set.addAll(select.getTableNames());
		}
		return set;
	}

	public boolean hasNot() {
		return hasNot;
	}

	public void setHasNot(boolean hasNot) {
		this.hasNot = hasNot;
	}

	public ConstantSet getSet() {
		return set;
	}
	
	public SelectStatement getSelect() {
		return select;
	}

	public void setSelect(SelectStatement select) {
		this.select = select;
		this.select.setParent(this);
	}

	public void setSet(ConstantSet set) {
		this.set = set;
		this.set.setParent(this);
	}
	
	public boolean isConstant() {
		return getSet() != null;
	}

	@Override
	public List<InCompare> getSubSelects() {
		if(!isConstant()) {
			List<InCompare> result = new ArrayList<InCompare>(1);
			result.add(this);
			return result;
		}
		return super.getSubSelects();
	}

	@Override
	public Kind getKind() {
		return Kind.IN_OPERATOR;
	}
	
	@Override
	public String toSql() {
		return getFieldRef().toSql() + (hasNot() ? " NOT " : "") + " IN (" + (set != null ? set.toSql() : select.toSql()) + ")";
	}
}
