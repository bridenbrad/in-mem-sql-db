/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.demo.DsvTextGenerator;
import com.tirion.executor.job.Job;
import com.tirion.executor.task.AbstractTask;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class GenericDsvGenerator extends AbstractTask<Void> {

	private final int rowCount;
	private final Entity entity;
	
	public GenericDsvGenerator(Job job, Entity entity, int rowCount) {
		super(job);
		this.rowCount = rowCount;
		this.entity = entity;
	}

	@Override
	protected Void callInternal() throws Exception {
		DsvTextGenerator generator = new DsvTextGenerator();
		generator.generate(entity, rowCount);
		return null;
	}
}
