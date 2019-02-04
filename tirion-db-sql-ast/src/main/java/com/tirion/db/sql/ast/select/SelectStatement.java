/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.Fields;
import com.tirion.db.sql.ast.common.Enriched;
import com.tirion.db.sql.ast.common.Relation;
import com.tirion.db.sql.ast.common.TableAware;
import com.tirion.db.sql.ast.context.Context;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class SelectStatement extends AbstractNode implements Relation, TableAware {
	
	private static final long serialVersionUID = 4419443007008418051L;
	
	private SelectClause selectClause;
	private FromClause fromClause;
	private JoinClause joinClause;
	private WhereClause whereClause;
	private GroupByClause groupByClause;
	private HavingClause havingClause;
	private OrderByClause orderByClause;
	private LimitClause limitClause;
	

//	@Enriched
//	@JsonProperty
//	private Tx tx;
	
	/**
	 * Logical output of this SELECT. Names of columns are 
	 * aliases. Name of entity is dynamically generated.
	 */
	@Enriched
	@JsonProperty
	private Entity output;
	
	/**
	 * Physical tuple that is expected to leave this statement.
	 * Is null in case there is GROUP BY.
	 */
	@Enriched
	private Fields input;
	
	/**
	 * Alias name.
	 */
	public abstract List<Field> getProjectionsFor(String relationName);
	
	/**
	 * Build input/output {@link Fields} definitions and
	 * projection map. In case GROUP BY is present context is
	 * one before grouping.
	 */
	public abstract void optimize(Context ctx);

	/**
	 * Logical output i.e. what query requested. This might
	 * differ from physical tuple that arrives into root
	 * operator.
	 */
	public final Entity getOutput() {
		return output;
	}

	public final void setOutput(Entity output) {
		this.output = output;
	}
	
	public Fields getInput() {
		return input;
	}

	public void setInput(Fields input) {
		this.input = input;
	}

//	public final Tx getTx() {
//		return tx;
//	}
//
//	/**
//	 * Should be called by {@link Engine} before semantic 
//	 * analysis.
//	 */
//	public final void setTx(Tx tx) {
//		this.tx = tx;
//	}

	public final boolean hasJoinClause() {
		return getJoinClause() != null;
	}
	
	public final boolean hasHavingClause() {
		return getHavingClause() != null;
	}

	public final JoinClause getJoinClause() {
		return joinClause;
	}

	public final void setJoinClause(JoinClause joinClause) {
		this.joinClause = joinClause;
	}

	public final HavingClause getHavingClause() {
		return havingClause;
	}

	public final void setHavingClause(HavingClause havingClause) {
		this.havingClause = havingClause;
	}

	public final SelectClause getSelectClause() {
		return selectClause;
	}

	public final void setSelectClause(SelectClause selectClause) {
		this.selectClause = selectClause;
	}

	public final FromClause getFromClause() {
		return fromClause;
	}

	public final void setFromClause(FromClause fromClause) {
		this.fromClause = fromClause;
	}

	public final WhereClause getWhereClause() {
		return whereClause;
	}

	public final void setWhereClause(WhereClause whereClause) {
		this.whereClause = whereClause;
	}

	public final GroupByClause getGroupByClause() {
		return groupByClause;
	}

	public final void setGroupByClause(GroupByClause groupByClause) {
		this.groupByClause = groupByClause;
	}
	
	public final boolean hasGroupByClause() {
		return getGroupByClause() != null;
	}

	public final OrderByClause getOrderByClause() {
		return orderByClause;
	}

	public final void setOrderByClause(OrderByClause orderByClause) {
		this.orderByClause = orderByClause;
	}
	 
	public final boolean hasOrderByClause() {
		return getOrderByClause() != null;
	}

	public final LimitClause getLimitClause() {
		return limitClause;
	}

	public final void setLimitClause(LimitClause limitClause) {
		this.limitClause = limitClause;
	}
	
	public final boolean hasLimitClause() {
		return getLimitClause() != null;
	}
	
	public final boolean hasWhereClause() {
		return getWhereClause() != null;
	}
	
	@Override
	public final boolean isSelect() {
		return true;
	}

	@Override
	public final Kind getKind() {
		return Kind.SELECT_STATEMENT;
	}

	@Override
	public final String toSql() {
		return 	  selectClause.toSql() +
				" " + fromClause.toSql() +
				" " + (hasJoinClause() ? getJoinClause().toSql() : "") +
				" " + (hasWhereClause() ? getWhereClause().toSql() : "") +
				" " + (hasGroupByClause() ? getGroupByClause().toSql() : "") +
				" " + (hasHavingClause() ? getHavingClause().toSql() : "") +
				" " + (hasOrderByClause() ? getOrderByClause().toSql() : "") +
				" " + (hasLimitClause() ? getLimitClause().toSql() : "");
	}
}

