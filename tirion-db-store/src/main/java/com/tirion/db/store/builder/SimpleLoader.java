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
import com.tirion.db.common.Config;
import com.tirion.db.common.Constants;
import com.tirion.db.store.appender.Appender;
import com.tirion.db.store.builder.transformation.TransformerFactory;
import com.tirion.db.store.builder.transformation.context.TransformationContext;
import com.tirion.db.store.builder.transformation.slice.TextLinesSlice;
import com.tirion.db.store.table.Table;
import com.tirion.db.store.table.data.SimpleTableData;
import com.tirion.db.store.table.data.TableData;
import com.tirion.executor.job.Job;
import com.tirion.file.FilesTextReader;
import com.tirion.file.TextReader;

public final class SimpleLoader implements Loader {
	
	private final Entity entity;
	private final Table table;
	private final List<File> files;
	private final Config config;
	private final Job job;
	private final TransformerFactory factory;
	
	private TransformationContext ctx;
	
	public SimpleLoader(Entity entity, Table table, List<File> files, Config config, Job job, TransformerFactory factory) {
		super();
		this.entity = entity;
		this.table = table;
		this.files = files;
		this.config = config;
		this.job = job;
		this.factory = factory;
	}

	@Override
	public void begin() {
		ctx = factory.newContext(entity, config, job);
		TextReader reader = new FilesTextReader(files);
		reader.init();
		try {
			consume(reader);
		} finally {			
			reader.shutdown();
		}
	}

	@Override
	public void commit() {
		TableData tableData = new SimpleTableData(ctx.getSlices(), ctx.getTokenMapProxies());
		Appender<TableData> appender = table.getAppender();
		appender.append(tableData);
		ctx = null;
	}
	
	@Override
	public void rollback() {
		ctx = null;
	}

	private void consume(TextReader reader) {
		int sliceId = 0;
		List<String> lines = new ArrayList<String>(Constants.ROWS_PER_PAGE);
		while(true) {
			String line = reader.next();
			if(line == null) {
				break;
			}
			lines.add(line);
			if(lines.size() == Constants.ROWS_PER_PAGE) {
				processLines(sliceId++, lines);
				lines = new ArrayList<String>(Constants.ROWS_PER_PAGE);
			}
		}
		if(!lines.isEmpty()) {
			processLines(sliceId, lines);
		}
	}
	
	private void processLines(int sliceId, List<String> lines) {
		ctx.onSlice(new TextLinesSlice(sliceId, lines));
	}
}
