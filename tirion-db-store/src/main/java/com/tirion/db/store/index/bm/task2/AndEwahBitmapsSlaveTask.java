/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.index.bm.task2;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.common.bitmap.CompressedBitmap;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class AndEwahBitmapsSlaveTask extends BitmapsSlaveTask {
	
	public AndEwahBitmapsSlaveTask(Job job, int start, int end, Bitmaps left, Bitmaps right) {
		super(job, start, end, left, right);
	}

	@Override
	protected Bitmaps callInternal() throws Exception {
		Bitmaps result = new Bitmaps(end-start);
		for (int i = start; i < end; i++) {
			Bitmap l = left.get(i);
			Bitmap r = right.get(i);
			if(l == null || right == null) {
				result.append((Bitmap)null);
			} else {
				EWAHCompressedBitmap lewah = l.getUnderlying();
				EWAHCompressedBitmap rewah = r.getUnderlying();
				result.append(new CompressedBitmap(lewah.and(rewah)));
			}
		}
		return result;
	}
}
