/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store;

import java.util.List;
import java.util.SortedSet;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.store.table.Table;
import com.tirion.db.tx.Scope;

/**
 * All components within store should operate on native Java
 * types only i.e. date & timestamp data types are never
 * dealt directly in their form.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Store {
	
	void init();
	
	void shutdown();
	
	void createTable(Entity entity);
	
	/**
	 * Will truncate table.
	 */
	void dropTable(String tableName);
	
	Table getTable(String tableName);
	
	Scope getScope(String tableName);
	
	List<Scope> getScopes(SortedSet<String> tableNames);
}
