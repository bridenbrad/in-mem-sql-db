/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.inmemory;

import junit.framework.TestCase;

import com.tirion.common.type.Type;
import com.tirion.db.catalog.Catalog;
import com.tirion.db.catalog.model.SimpleEntity;
import com.tirion.db.catalog.model.SimpleField;

public class InMemoryCatalogTest extends TestCase {

	public void test() {
		Catalog catalog = new InMemoryCatalog();
		
		assertTrue(catalog.hasFunction("max"));
		assertTrue(catalog.hasFunction("MAX"));
		assertTrue(catalog.hasFunction("MaX"));
		
		final String tableName = "test_table";
		SimpleField field = new SimpleField("field1", tableName, Type.DATE, 0);
		SimpleEntity entity = new SimpleEntity(tableName);
		entity.append(field);
		
		catalog.register(entity);
		
		assertTrue(entity.fieldCount() == 1);
		assertTrue(entity.hasField("field1"));
		assertTrue(catalog.hasEntity(tableName));
		
		catalog.unregister(tableName);
	
		assertTrue(!catalog.hasEntity(tableName));
	}
}
