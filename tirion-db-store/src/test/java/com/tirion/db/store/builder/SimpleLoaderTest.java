/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.EntityUtil;
import com.tirion.db.common.Config;
import com.tirion.db.common.Constants;
import com.tirion.db.demo.DsvTextGenerator;
import com.tirion.db.store.BaseSpringTest;
import com.tirion.db.store.dump.SimpleTableDumper;
import com.tirion.db.store.dump.TableDumper;
import com.tirion.db.store.table.Table;

public class SimpleLoaderTest extends BaseSpringTest {
	
	private static final String FILE_NAME = "trade-tokenize-all.properties";

	@Override
	protected String getSpringConfigFileName() {
		return "transformer/test.xml";
	}
	
	public void test() throws Exception {
		Entity entity = EntityUtil.parse(new File("src/test/resources/entities/" + FILE_NAME));
		store.createTable(entity);
		
		List<File> files = new ArrayList<File>();
		files.add(getInputFile(entity, Constants.ROWS_PER_PAGE * 2));
		
		Loader loader = new SimpleLoader(entity, store.getTable("trade"), 
				files, Config.DEFAULT, jobFactory.newJob(), transformerFactory);
		loader.begin();
		loader.commit();
		
		Table table = store.getTable("trade");
		TableDumper dumper = new SimpleTableDumper();
		dumper.dumpBasic(table, new File("/tmp/"));
	}
	
	private File getInputFile(Entity entity, int rowCount) {
		DsvTextGenerator generator = new DsvTextGenerator();
		generator.generate(entity, rowCount);
		return new File("/tmp/" + entity.getName() + ".txt");
	}
}
