/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.index.bm.task2;

import com.tirion.common.bitmap.Bitmaps;
import com.tirion.executor.job.Job;
import com.tirion.executor.task.AbstractTask;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class RowIdProjectingTask<T> extends AbstractTask<T> {

	protected final Bitmaps bitmaps;

	public RowIdProjectingTask(Job job, Bitmaps bitmaps) {
		super(job);
		this.bitmaps = bitmaps;
	}
}
