/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector;

import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.ValueExtractors;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractProjector implements Projector {

	protected Job job;
	protected ValueExtractors valueExtractors;
		
	public final void setJob(Job job) {
		this.job = job;
	}

	/**
	 * Should be called by {@link Planner}.
	 */
	public final void setValueExtractors(ValueExtractors valueExtractors) {
		this.valueExtractors = valueExtractors;
	}
}
