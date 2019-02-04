/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.set.SmartSet;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FieldRefsAware;
import com.tirion.db.sql.ast.common.TableAware;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;
import com.tirion.db.sql.ast.expression.compare.InCompare;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class WhereClause extends AbstractNode implements FieldRefsAware, TableAware {

	private static final long serialVersionUID = 704213468206554345L;
	
	@JsonProperty
	private BoolExpression expression;
	
	@JsonProperty
	private boolean filtersPushable = false;
	@JsonProperty
	private Map<String, BoolExpression> relationToPushableExpression;
	
	/**
	 * Returns all INs which operate on sub-select. 
	 */
	public List<InCompare> getInSubSelects() {
		return expression.getSubSelects();
	}
	
	/**
	 * For IN sub-query.
	 */
	@Override
	public SortedSet<String> getTableNames() {
		return expression.getTableNames();
	}

	public BoolExpression getExpression() {
		return expression;
	}
	
	public void setExpression(BoolExpression boolExpression) {
		this.expression = boolExpression;
		this.expression.setParent(this);
	}
	
	public void setFiltersPushable(boolean filtersPushable) {
		this.filtersPushable = filtersPushable;
	}
	
	public void setRelationToPushableExpression(Map<String, BoolExpression> relationToPushableExpression) {
		this.relationToPushableExpression = relationToPushableExpression;
	}

	/**
	 * Returns expression for given table name. Returns null in case
	 * there is no expression for that table. Table name is one
	 * from alias.
	 */
	public BoolExpression getPushableFiltersFor(String relation) {
		return relationToPushableExpression.get(relation);
	}
	
	/**
	 * Should be used only in case filters are not pushable
	 * and we need to filter tuples instead of columns.
	 */
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		return expression.getRequiredFieldRefs();
	}

	@Override
	public Kind getKind() {
		return Kind.WHERE_CLAUSE;
	}
	
	/**
	 * Returns true if all filters are AND-ed and each filter
	 * touches only one relation. Indicates that filter may
	 * be pushed, does not mean that filters should be pushed.
	 */
	public boolean filtersArePushable() {
		return filtersPushable;
	}

	@Override
	public String toSql() {
		return "WHERE " + expression.toSql();
	}
}
