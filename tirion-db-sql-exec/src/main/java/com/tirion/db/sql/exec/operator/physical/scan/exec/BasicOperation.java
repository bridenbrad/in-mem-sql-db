/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.exec;

import com.tirion.db.store.column.Column;
import com.tirion.executor.job.Job;

/**
 * Contains reference to {@link Column}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BasicOperation extends AbstractOperation {

	private final Column column;

	public BasicOperation(Job job, Column column) {
		super(job);
		this.column = column;
	}

	protected final Column getColumn() {
		return column;
	}
}
