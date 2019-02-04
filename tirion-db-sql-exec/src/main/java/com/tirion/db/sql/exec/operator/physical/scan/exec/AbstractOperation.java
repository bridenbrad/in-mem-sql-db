/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.exec;

import com.tirion.common.NotYetImplementedException;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractOperation implements Operation {

	private final Job job;

	public AbstractOperation(Job job) {
		super();
		this.job = job;
	}

	protected final Job getJob() {
		return job;
	}
	
	protected final Runtime getRuntime() {
		throw new NotYetImplementedException();
	}
}
