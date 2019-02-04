/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.transformer;

import com.tirion.common.runtime.Runtime;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.options.Options;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractTransformer implements Transformer {

	private final Runtime runtime;
	private final Job job;
	private final Entity entity;
	private final Options[] options;

	public AbstractTransformer(Entity entity, Runtime runtime, Job job) {
		super();
		this.entity = entity;
		this.runtime = runtime;
		this.job = job;
		options = buildOptions(entity);
	}

	protected final Entity getEntity() {
		return entity;
	}

	protected final Runtime getRuntime() {
		return runtime;
	}

	protected final Job getJob() {
		return job;
	}
	
	protected final boolean shouldBeOffHeap(int index) {
		return options[index].isOffHeap();
	}
	
	protected final boolean shouldBeCompressed(int index) {
		return options[index].isCompressed();
	}
	
	protected final boolean shouldBeTokenized(int index) {
		return options[index].isTokenized();
	}
	
	private Options[] buildOptions(Entity entity) {
		Options[] options = new Options[entity.fieldCount()];
		for (int i = 0; i < options.length; i++) {
			options[i] = entity.getField(i).getOptions();
		}
		return options;
	}
	
}
