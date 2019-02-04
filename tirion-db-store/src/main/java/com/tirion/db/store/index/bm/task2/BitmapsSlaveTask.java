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
public abstract class BitmapsSlaveTask extends AbstractTask<Bitmaps> {

	protected final int start;
	protected final int end;
	protected final Bitmaps left;
	protected final Bitmaps right;
	
	public BitmapsSlaveTask(Job job, int start, int end, Bitmaps left, Bitmaps right) {
		super(job);
		this.start = start;
		this.end = end;
		this.left = left;
		this.right = right;
	}
}
