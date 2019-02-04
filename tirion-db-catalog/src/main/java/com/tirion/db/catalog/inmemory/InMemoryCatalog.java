/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.db.catalog.AbstractCatalog;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.EntityType;
import com.tirion.db.catalog.model.Field;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InMemoryCatalog extends AbstractCatalog {
	
	private static final Logger log = LoggerFactory.getLogger(InMemoryCatalog.class);
	
	private final Map<String, Entity> map;
	private final Set<String> tableNames;
	
	public InMemoryCatalog() {
		super();
		map = new HashMap<String, Entity>();
		tableNames = new HashSet<String>();
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}
	
	@Override
	public Set<String> getTableNames() {
		return tableNames;
	}

	@Override
	public synchronized Collection<Entity> getTables() {
		return map.values();
	}

	@Override
	public synchronized void register(Entity entity) {
		if(map.containsKey(entity.getName())) {
			throw new IllegalArgumentException("Table '" + entity.getName() + "' already exists");
		}
		map.put(entity.getName(), entity);
		tableNames.add(entity.getName());
		log.info("Successfully created table '" + entity.toString() + "'");
	}

	@Override
	public synchronized void unregister(String tableName) {
		tableName = tableName.toLowerCase();
		Entity info = map.remove(tableName);
		if(info == null) {
			throw new IllegalArgumentException("Table '" + tableName + "' already exists");
		}
		tableNames.remove(tableName);
		log.info("Successfully dropped table '" + tableName + "'");
	}

	@Override
	public synchronized boolean hasEntity(String tableName) {
		tableName = tableName.toLowerCase();
		return map.containsKey(tableName);
	}

	@Override
	public synchronized Entity getEntity(String tableName) {
		tableName = tableName.toLowerCase();
		Entity info = map.get(tableName);
		if(info == null) {
			throw new IllegalArgumentException("Table '" + tableName + "' does not exist");
		}
		return info;
	}

	@Override
	public synchronized boolean isLarge(String tableName) {
		tableName = tableName.toLowerCase();
		return getEntity(tableName).getType() == EntityType.LARGE;
	}

	@Override
	public boolean hasField(String tableName, String fieldName) {
		tableName = tableName.toLowerCase();
		fieldName = fieldName.toLowerCase();
		return getEntity(tableName).hasField(fieldName);
	}

	@Override
	public Field getField(String tableName, String fieldName) {
		tableName = tableName.toLowerCase();
		fieldName = fieldName.toLowerCase();
		return getEntity(tableName).getField(fieldName);
	}
}
