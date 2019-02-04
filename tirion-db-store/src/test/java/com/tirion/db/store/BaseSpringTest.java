/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tirion.db.store.allocator.Allocator;
import com.tirion.db.store.builder.transformation.SimpleTransformerFactory;
import com.tirion.db.store.builder.transformation.TransformerFactory;
import com.tirion.executor.job.JobFactory;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BaseSpringTest extends TestCase {

	private ApplicationContext context;
	private com.tirion.common.runtime.Runtime runtime;
	protected Store store;
	protected JobFactory jobFactory;
	protected Allocator allocator;
	protected TransformerFactory transformerFactory;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = new ClassPathXmlApplicationContext(getSpringConfigFileName());
		runtime = context.getBean(com.tirion.common.runtime.Runtime.class);
		store = getBean(Store.class);
		jobFactory = getBean(JobFactory.class);
		allocator = getBean(Allocator.class);
		transformerFactory = new SimpleTransformerFactory(allocator, store, runtime);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected final com.tirion.common.runtime.Runtime getRuntime() {
		return runtime;
	}
	
	protected final <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

	protected abstract String getSpringConfigFileName();
}
