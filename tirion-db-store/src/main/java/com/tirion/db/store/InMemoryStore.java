/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.runtime.Runtime;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.common.Constants;
import com.tirion.db.store.table.InMemoryTable;
import com.tirion.db.store.table.Table;
import com.tirion.db.tx.Scope;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class InMemoryStore implements Store {
	
	private static final Logger log = LoggerFactory.getLogger(InMemoryStore.class);

	private final Map<String, Table> map;
	
	private Runtime runtime;

	public InMemoryStore() {
		super();
		map = new HashMap<String, Table>();
	}
	
	public void setRuntime(Runtime runtime) {
		this.runtime = runtime;
	}

	@Override
	public void init() {
		log.info("Number of rows per page is '" + Constants.ROWS_PER_PAGE + "'");
	}

	@Override
	public void shutdown() {
	}

	@Override
	public synchronized void createTable(Entity entity) {
		if(map.containsKey(entity.getName())) {
			throw new IllegalArgumentException("Duplicate table '" + entity.getName() + "'");
		}
		map.put(entity.getName(), new InMemoryTable(entity, runtime));
	}

	@Override
	public synchronized void dropTable(String tableName) {
		Table table = map.remove(tableName);
		if(table == null) {
			throw new IllegalArgumentException("Table '" + tableName + "' not found");
		}
		table.truncate();
	}

	@Override
	public synchronized Table getTable(String tableName) {
		Table table = map.get(tableName);
		if(table == null) {
			throw new IllegalArgumentException("Table '" + tableName + "' not found");
		}
		return table;
	}
	
	@Override
	public synchronized Scope getScope(String tableName) {
		Table table = getTable(tableName);
		return new Scope(table.getName(), table.getPageCount());
	}

	@Override
	public List<Scope> getScopes(SortedSet<String> tableNames) {
		List<Scope> scopes = new ArrayList<Scope>(tableNames.size());
		for(String tableName : tableNames) {
			getScope(tableName);
		}
		return scopes;
	}
}
