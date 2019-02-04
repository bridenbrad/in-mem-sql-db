/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.exec;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.bitmap.Bitmaps;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.RowIdSource;
import com.tirion.db.sql.exec.operator.physical.scan.rowid.source.split.SplittableRowIdSource;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class MainOperation {

	private final Job job;
	private final Operation op;

	public MainOperation(Job job, Operation op) {
		super();
		this.job = job;
		this.op = op;
	}

	public SplittableRowIdSource execute() {
		try {
			return null;//executeInternal();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private RowIdSource executeInternal() throws Exception {
//		if(op instanceof CompositeOperation) {
//			CompositeOperation comp = (CompositeOperation) op;
//			return comp.execute();
//		} else if(op instanceof NonIndexedOperation) {
//			NonIndexedOperation nonInd = (NonIndexedOperation) op;
//			return nonInd.execute(null);
// 		} else {
// 			IndexedOperation indexed = (IndexedOperation) op;
// 			Bitmaps bitmaps = indexed.execute();
//// 			RowIdProjectingMasterTask task = new RowIdProjectingMasterTask(job, bitmaps);
//// 			return task.call();
// 		}
 			throw new NotYetImplementedException();
	}
}
