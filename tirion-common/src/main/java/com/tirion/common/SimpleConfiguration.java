/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleConfiguration implements Configuration {

	private final int minPageCountForAndBitmapTask;
	private final int minPageCountForOrBitmapTask;
	private final int minPageCountForRowIdProjectingSlaveTask;
	private final int minTuplesPerProjectorSlaveTask;
	private final int batchSizeForTupleProjectorSlaveTask;
	private final int scanOperatorUnconsumedTuples;
	private final int groupByOperatorParallelBuckets;
	
	public SimpleConfiguration(
			int minPageCountForAndBitmapTask, 
			int minPageCountForOrBitmapTask,
			int minPageCountForRowIdProjectingSlaveTask, 
			int minTuplesPerProjectorSlaveTask, 
			int batchSizeForTupleProjectorSlaveTask, 
			int scanOperatorUnconsumedTuples, 
			int groupByOperatorParallelBuckets) {
		super();
		this.minPageCountForAndBitmapTask = minPageCountForAndBitmapTask;
		this.minPageCountForOrBitmapTask = minPageCountForOrBitmapTask;
		this.minPageCountForRowIdProjectingSlaveTask = minPageCountForRowIdProjectingSlaveTask;
		this.minTuplesPerProjectorSlaveTask = minTuplesPerProjectorSlaveTask;
		this.batchSizeForTupleProjectorSlaveTask = batchSizeForTupleProjectorSlaveTask;
		this.scanOperatorUnconsumedTuples = scanOperatorUnconsumedTuples;
		this.groupByOperatorParallelBuckets = groupByOperatorParallelBuckets;
	}

	@Override
	public int getGroupByOperatorParallelBuckets() {
		return groupByOperatorParallelBuckets;
	}

	@Override
	public int getMinPageCountForAndBitmapTask() {
		return    minPageCountForAndBitmapTask;
	}

	@Override
	public int getMinPageCountForOrBitmapTask() {
		return    minPageCountForOrBitmapTask;
	}

	@Override
	public int getMinPageCountForRowIdProjectingSlaveTask() {
		return    minPageCountForRowIdProjectingSlaveTask;
	}

	@Override
	public int getMinTuplesPerTupleProjectorSlaveTask() {
		return    minTuplesPerProjectorSlaveTask;
	}

	@Override
	public int getBatchSizeForTupleProjectorSlaveTask() {
		return 	  batchSizeForTupleProjectorSlaveTask;
	}

	@Override
	public int getScanOperatorUnconsumedTuples() {
		return    scanOperatorUnconsumedTuples;
	}
}
