/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.plan.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.sql.ast.common.EntityRef;
import com.tirion.db.sql.ast.context.Context;
import com.tirion.db.sql.ast.message.Message.MessageKind;
import com.tirion.db.sql.ast.select.FromClause;
import com.tirion.db.sql.ast.select.Join;
import com.tirion.db.sql.ast.select.SelectStatement;
import com.tirion.db.sql.ast.select.SmartSelectStatement;
import com.tirion.db.sql.ast.visitor.AbstractVisitor;

/**
 * Assigs missing entity aliases. Assigns name to sub-select
 * {@link Entity} definitions. Builds alias map. Collects 
 * all {@link Entity} definitions.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class EntityCollector extends AbstractVisitor {

	/**
	 * Key is alias.
	 */
	private final Map<String, String> aliasMap = new HashMap<String, String>();
	
	/**
	 * Key is alias.
	 */
	private final Map<String, Entity> entityMap = new HashMap<String, Entity>();
	
	public EntityCollector(Runtime runtime) {
		super(runtime);
	}

	public Collection<Entity> getEntities() {
		return entityMap.values();
	}
	
	public Map<String, String> getAliasMap() {
		return aliasMap;
	}

	@Override
	public void visit(SelectStatement select, Context ctx) {
		visit(select.getFromClause(), ctx);
		if(select.hasJoinClause()) {
			for(Join join : select.getJoinClause().getJoins()) {
				visit(join, ctx);
			}
		}
		SmartSelectStatement smartSelect = (SmartSelectStatement) select;
		smartSelect.setAliasMap(aliasMap);
		smartSelect.setEntityMap(entityMap);
	}

	@Override
	public void visit(FromClause from, Context ctx) {
		if(from.isTable()) {
			EntityRef entityRef = (EntityRef) from.getRelation();
			if(!from.hasAlias()) {
				from.setAlias(entityRef.getName());
			}
			processEntityRef(from.getAlias(), entityRef, ctx);
			from.setEntity(ctx.getEntity(from.getTableName()));
		} else {
			SelectStatement select = (SelectStatement) from.getRelation();
			processSubSelect(from.getAlias(), select);
		}
	}

	@Override
	public void visit(Join join, Context ctx) {
		if(join.isTable()) {
			EntityRef entityRef = (EntityRef) join.getRelation();
			if(!join.hasAlias()) {
				join.setAlias(entityRef.getName());
			}
			processEntityRef(join.getAlias(), entityRef, ctx);
		} else {
			SelectStatement select = (SelectStatement) join.getRelation();
			processSubSelect(join.getAlias(), select);
		}
	}
	
	private void processEntityRef(String alias, EntityRef entityRef, Context ctx) {
		if(!ctx.hasEntity(entityRef.getName())) {
			ctx.getListener().onMessage("Unknown entity '" + entityRef.getName() + "'", MessageKind.ERROR);
		}
		registerAlias(alias, entityRef.getName());
		registerEntity(alias, ctx.getEntity(entityRef.getName()).cloneMe(alias));
	}
	
	private void processSubSelect(String alias, SelectStatement select) {
		select.setOutput(select.getOutput().cloneMe(alias));
		registerAlias(alias, alias);
		registerEntity(alias, select.getOutput());
	}
	
	private void registerEntity(String alias, Entity entity) {
		if(alias == null || entity == null) {
			throw new NullPointerException();
		}
		if(entityMap.put(alias, entity) != null) {
			throw new IllegalStateException("Duplicate alias '" + alias + "' for entity '" + entity.getName() + "'");
		}
	}
	
	private void registerAlias(String alias, String name) {
		if(alias == null || name == null) {
			throw new NullPointerException();
		}
		if(aliasMap.put(alias, name) != null) {
			throw new IllegalStateException("Duplicate alias '" + alias + "' for name '" + name + "'");
		}
	}
}
