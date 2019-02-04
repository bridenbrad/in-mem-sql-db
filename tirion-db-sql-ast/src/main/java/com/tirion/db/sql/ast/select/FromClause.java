/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.select;

import java.util.SortedSet;
import java.util.TreeSet;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.sql.ast.AbstractNode;
import com.tirion.db.sql.ast.common.AliasAware;
import com.tirion.db.sql.ast.common.Enriched;
import com.tirion.db.sql.ast.common.EntityRef;
import com.tirion.db.sql.ast.common.Relation;
import com.tirion.db.sql.ast.common.RelationAware;
import com.tirion.db.sql.ast.common.TableAware;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class FromClause extends AbstractNode implements RelationAware, TableAware, AliasAware {

	private static final long serialVersionUID = -9160170852517374354L;
	
	// one of these has to be null
	@JsonProperty
	private EntityRef entityRef;
	@JsonProperty
	private SelectStatement subSelect;
	
	@JsonProperty
	private String alias;
	
	@Enriched
	@JsonProperty
	private Entity entity;
	
	@Override
	public SortedSet<String> getTableNames() {
		SortedSet<String> set = new TreeSet<String>();
		if(isSubSelect()) {
			set.addAll(subSelect.getTableNames());
		} else {
			set.add(entityRef.getName());
		}
		return set;
	}
	
	@Override
	public Relation getRelation() {
		if(entityRef != null) {
			return entityRef;
		}
		return subSelect;
	}
	
	public Entity getEntity() {
		if(subSelect != null) {
			return subSelect.getOutput();
		}
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
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
	
	public void setAlias(String alias) {
		this.alias = ( alias != null ? alias.toLowerCase() : null);
	}

	public SelectStatement getSubSelect() {
		return subSelect;
	}

	public void setSubSelect(SelectStatement subSelect) {
		this.subSelect = subSelect;
	}

	public EntityRef getTable() {
		if(entityRef == null) {
			throw new NullPointerException();
		}
		return entityRef;
	}

	public void setTable(EntityRef table) {
		this.entityRef = table;
	}
	
	/**
	 * Returns name of underlying physical table.
	 */
	public String getTableName() {
		return entityRef.getName();
	}
	
	@Override
	public String getAlias() {
		return alias;
	}
	
	@Override
	public boolean hasAlias() {
		return alias != null;
	}

	@Override
	public Kind getKind() {
		return Kind.FROM_CLAUSE;
	}

	@Override
	public String toSql() {
		return "FROM " + (subSelect == null ? entityRef.toSql() : "(" + subSelect.toSql() + ")")
				+ (hasAlias() ? " AS " + alias : "");
	}
}
