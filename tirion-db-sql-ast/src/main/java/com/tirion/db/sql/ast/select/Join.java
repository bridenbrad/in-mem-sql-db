/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.set.SmartSet;
import com.tirion.db.common.JoinType;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.Fields;
import com.tirion.db.sql.ast.common.AliasAware;
import com.tirion.db.sql.ast.common.Enriched;
import com.tirion.db.sql.ast.common.EntityRef;
import com.tirion.db.sql.ast.common.FieldRef;
import com.tirion.db.sql.ast.common.FieldRefsAware;
import com.tirion.db.sql.ast.common.Relation;
import com.tirion.db.sql.ast.common.RelationAware;
import com.tirion.db.sql.ast.common.TableAware;
import com.tirion.db.sql.ast.expression.bool.BoolExpression;

/**
 * TODO hash join push down
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Join extends AbstractNode implements RelationAware, TableAware, AliasAware, FieldRefsAware {
	
	private static final long serialVersionUID = 2788554672325811220L;
	
	// one of these has to be null;
	@JsonProperty
	private EntityRef entityRef;
	@JsonProperty
	private SelectStatement subSelect;
	
	@JsonProperty
	private String alias;
	
	@JsonProperty
	private BoolExpression condition;
	@JsonProperty
	private JoinType joinType;
	
	@Enriched
	@JsonProperty
	private Fields leftInput;
	
	@Enriched
	@JsonProperty
	private Fields rightInput;
	
	@Enriched
	@JsonProperty
	private Fields output; 
	
	@Enriched
	@JsonProperty
	private boolean hashCandidate = false;

	public void setLeftInput(Fields leftInput) {
		this.leftInput = leftInput;
	}

	public void setRightInput(Fields rightInput) {
		this.rightInput = rightInput;
	}

	public void setOutput(Fields output) {
		this.output = output;
	}
	
	public Fields getLeftInput() {
		return leftInput;
	}

	public Fields getRightInput() {
		return rightInput;
	}
	
	public Fields getOutput() {
		return output;
	}

	/**
	 * {@link FieldRef}s from JOIN condition.
	 */
	@Override
	public SmartSet<FieldRef> getRequiredFieldRefs() {
		return condition.getRequiredFieldRefs();
	}

	@Override
	public SortedSet<String> getTableNames() {
		SortedSet<String> set = new TreeSet<String>();
		if(isSubSelect()) {
			set.addAll(subSelect.getTableNames());
		} else {
			set.add(entityRef.getName());
		}
		set.addAll(condition.getTableNames());
		return set;
	}

	public boolean isInner() {
		return joinType == JoinType.INNER;
	}
	
	@Override
	public Relation getRelation() {
		if(entityRef != null) {
			return entityRef;
		}
		return subSelect;
	}

	/**
	 * For hash join for left {@link KeyBuilder}. These are all
	 * {@link FieldRef}s from left relation that participate
	 * in join expression.
	 */
	public Set<FieldRef> getLeftJoinConditionFieldRefs() {
		return new HashSet<FieldRef>(getCondition().getFieldRefs(getAlias(), false));
	}
	
	/**
	 * For hash join for right {@link KeyBuilder}. These are all
	 * {@link FieldRef}s from right relation that participate
	 * in join expression.
	 */
	public Set<FieldRef> getRightJoinConditionFieldRefs() {
		return new HashSet<FieldRef>(getCondition().getFieldRefs(getAlias(), true));
	}

	public boolean isHashCandidate() {
		return hashCandidate;
	}

	@Override
	public boolean hasAlias() {
		return alias != null;
	}
	
	@Override
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		if(alias != null) {			
			this.alias = alias.toLowerCase();
		}
	}
	
	/**
	 * Assumes physical table is used and not sub-select.
	 */
	public String getTableName() {
		return entityRef.getName();
	}

	public void setHashCandidate(boolean hashCandidate) {
		this.hashCandidate = hashCandidate;
	}

	public SelectStatement getSubSelect() {
		return subSelect;
	}

	public void setSubSelect(SelectStatement subSelect) {
		this.subSelect = subSelect;
	}
	
	/**
	 * True if it refers to physical table.
	 */
	public boolean isTable() {
		return entityRef != null;
	}
	
	public boolean isSubSelect() {
		return subSelect != null;
	}

	public EntityRef getTable() {
		return entityRef;
	}

	public void setTable(EntityRef table) {
		this.entityRef = table;
	}

	public BoolExpression getCondition() {
		return condition;
	}

	public void setCondition(BoolExpression condition) {
		this.condition = condition;
	}
	
	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}

	@Override
	public Kind getKind() {
		return Kind.JOIN;
	}
	
	@Override
	public String toSql() {
		return joinType.toString() + " JOIN " + (entityRef != null ? entityRef.toSql() : "(" + subSelect.toSql() + ")") 
				+ (hasAlias() ? " AS " + getAlias() : "")
				+ " ON (" + condition.toSql() + ")";
	}
}
