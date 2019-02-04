/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation;

import java.io.File;
import java.util.List;

import com.tirion.common.file.FileUtil;
import com.tirion.db.common.Config;
import com.tirion.db.demo.DsvTextGenerator;
import com.tirion.db.demo. models.Trade;
import com.tirion.db.store.BaseSpringTest;
import com.tirion.db.store.Store;
import com.tirion.db.store.allocator.Allocator;
import com.tirion.db.store.builder.transformation.context.TransformationContext;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.slice.TextLinesSlice;
import com.tirion.executor.job.Job;
import com.tirion.executor.job.JobFactory;

public final class TransformerTest extends BaseSpringTest {

	@Override
	protected String getSpringConfigFileName() {
		return "transformer/test.xml";
	}

	public void test() {
		Store store = super.getBean(Store.class);
		Job job = super.getBean(JobFactory.class).newJob();
		Allocator allocator = super.getBean(Allocator.class);
		store.createTable(Trade.ENTITY);

		TransformerFactory factory = new SimpleTransformerFactory(allocator, store, getRuntime());
		TransformationContext ctx = factory.newContext(Trade.ENTITY, new Config(), job);

		Slice slice = new TextLinesSlice(0, getLines());
		ctx.onSlice(slice);
	}
	
	private List<String> getLines() {
		DsvTextGenerator generator = new DsvTextGenerator();
		generator.generate(Trade.ENTITY, 256);
		return FileUtil.readTextLines(new File("/tmp/" + Trade.ENTITY.getName()
				+ ".txt"));
	}
}
