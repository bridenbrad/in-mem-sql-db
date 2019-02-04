/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.tirion.db.catalog.Catalog;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.catalog.model.function.Function;
import com.tirion.db.sql.ast.message.MessageListener;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleContext implements Context {
	
	private static final AtomicLong COUNTER = new AtomicLong();
	
	private final Set<String> entityNames = new HashSet<String>();
	private final List<Entity> entities = new ArrayList<Entity>();
	private final List<Field> fields = new ArrayList<Field>();
	
	private final Catalog catalog;
	private final MessageListener listener;
	
	private boolean detached = false;
	private boolean isInWhereScope;
	private boolean isInHavingScope;
	private boolean isInJoinScope;
		
	public SimpleContext(Catalog catalog, MessageListener listener) {
		this.catalog = catalog;
		this.listener = listener;
	}

	@Override
	public void register(Field field) {
		if(getFieldInternal(field.getOwner(), field.getName()) != null) {
			throw new IllegalArgumentException("Duplicate field '" + field.toString() + "'");			
		}
		fields.add(field);
	}
	
	@Override
	public void register(Entity entity) {
		if(getEntityInternal(entity.getName()) != null) {			
			throw new IllegalArgumentException("Duplicate entity '" + entity.getName() + "'");
		}
		entityNames.add(entity.getName());
		entities.add(entity);
	}
	
	@Override
	public String findOwnerFor(String fieldName) {
		String result = null;
		for(Entity entity : entities) {
			if(entity.hasField(fieldName)) {
				if(result != null) {
					throw new IllegalStateException("More than one entity has field '" + fieldName + "'");
				}
				result = entity.getName();
			}
		}
		if(result != null) {
			return result;
		}
		for(Field field : fields) {
			if(field.getName().equals(fieldName)) {
				if(result != null) {
					throw new IllegalStateException("More than one entity has field '" + fieldName + "'");
				}
				result = field.getOwner();
			}
		}
		if(result == null) {
			throw new IllegalStateException("Unable to find owner for field '" + fieldName + "'");
		}
		return result;
	}

	@Override
	public boolean hasEntity(String tableName) {
		if(getEntityInternal(tableName) != null) {
			return true;
		}
		if(detached) {
			return false;
		}
		return catalog.hasEntity(tableName);
	}

	@Override
	public Entity getEntity(String tableName) {
		Entity entity = getEntityInternal(tableName);
		if(entity != null) {
			return entity;
		}
		if(detached) {
			throw new IllegalArgumentException("Unknown table '" + tableName + "'");
		}
		return catalog.getEntity(tableName);
	}
	
	@Override
	public boolean hasField(String tableName, String fieldName) {
		if(getFieldInternal(tableName, fieldName) != null) {
			return true;
		}
		if(detached) {
			return false;
		}
		return catalog.hasField(tableName, fieldName);
	}

	@Override
	public Field getField(String tableName, String fieldName) {
		Field field = getFieldInternal(tableName, fieldName);
		if(field != null) {
			return field;
		}
		return catalog.getField(tableName, fieldName);
	}

	@Override
	public boolean isLarge(String tableName) {
		return catalog.isLarge(tableName);
	}

	@Override
	public Function getFunction(String functionName) {
		return catalog.getFunction(functionName);
	}

	@Override
	public boolean hasFunction(String functionName) {
		return catalog.hasFunction(functionName);
	}

	@Override
	public MessageListener getListener() {
		return listener;
	}
	@Override
	public String nextUniqueFunctionAlias() {
		return "_function_" + COUNTER.getAndIncrement();
	}
	
	@Override
	public String nextUniqueEntityName() {
		return "_entity_" + COUNTER.getAndIncrement();
	}

	@Override
	public String nextUniqueEntityName(String prefix) {
		return prefix + COUNTER.getAndIncrement();
	}

	@Override
	public boolean isInJoinScope() {
		return isInJoinScope;
	}

	public void setInJoinScope(boolean isInJoinScope) {
		this.isInJoinScope = isInJoinScope;
	}

	@Override
	public Context cloneMe() {
		return new SimpleContext(catalog, listener);
	}

	@Override
	public boolean isInWhereScope() {
		return isInWhereScope;
	}

	@Override
	public boolean isInHavingScope() {
		return isInHavingScope;
	}
	
	@Override
	public void detach() {
		detached = true;
	}

	@Override
	public void enterWhereScope() {
		isInWhereScope = true;
	}
	
	@Override
	public void leaveWhereScope() {
		isInWhereScope = false;
	}

	@Override
	public void enterHavingScope() {
		isInHavingScope = true;
	}
	
	@Override
	public void leaveHavingScope() {
		isInWhereScope = false;
	}

	@Override
	public void enterJoinScope() {
		isInJoinScope = true;
	}

	@Override
	public void leaveJoinScope() {
		isInJoinScope = false;
	}
	
	/**
	 * May return null. Will throw exception if there are multiple 
	 * definitions of field.
	 */
	private Field getFieldInternal(String owner, String fieldName) {
		Entity entity = getEntityInternal(owner);
		Field field = null;
		for(Field f : fields) {
			if(f.getName().equals(fieldName) && f.getOwner().equals(owner)) {
				if(field != null) {
					throw new IllegalStateException("Duplicate field '" + owner + "." + fieldName + "'");
				}
				field = f;
			}
		}
		if(entity != null && entity.hasField(fieldName) && field != null) {
			throw new IllegalStateException("Duplicate field " + owner + "." + field);
		}		
		if(entity != null && entity.hasField(fieldName)) {
			return entity.getField(fieldName);
		}
		return field;
	}
	
	/**
	 * May return null. Will throw exception if there is more than one
	 * entity in {@link List} of {@link Entity}s.
	 */
	private Entity getEntityInternal(String entityName) {
		Entity result = null;
		for(Entity entity : entities) {
			if(entity.getName().equals(entityName)) {
				if(result != null) {
					throw new IllegalStateException("Duplicate entity '" + entityName + "'");
				}
				result = entity; 
			}
		}
		return result;
	}
}
