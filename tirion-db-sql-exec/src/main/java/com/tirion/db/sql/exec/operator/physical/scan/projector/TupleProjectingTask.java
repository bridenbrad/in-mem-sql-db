/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector;

import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.ValueExtractors;
import com.tirion.db.sql.exec.tuple.sink.TupleListener;
import com.tirion.executor.job.Job;
import com.tirion.executor.task.AbstractTask;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class TupleProjectingTask extends AbstractTask<Void> {

	protected final int fieldCount;
	protected final TupleListener sink;
	protected final ValueExtractors extractors;
	
	public TupleProjectingTask(Job job, int fieldCount, TupleListener sink, ValueExtractors extractors) {
		super(job);
		this.fieldCount = fieldCount;
		this.sink = sink;
		this.extractors = extractors;
	}
}
