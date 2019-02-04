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
public interface Configuration {

	/**
	 * Minimum number of pages to perform AND on.
	 */
	int getMinPageCountForAndBitmapTask();
	
	/**
	 * Minimum number of pages to perform OR on.
	 */
	int getMinPageCountForOrBitmapTask();
	
	/**
	 * When converting bitmap list into row IDs.
	 */
	int getMinPageCountForRowIdProjectingSlaveTask();
	
	/**
	 * Minimum number of rows each task should project
	 * (from row ID into tuple) i.e. if row ID count
	 * is 10m, this might be 1m.
	 */
	int getMinTuplesPerTupleProjectorSlaveTask();
	
	/**
	 * How many rows at a time to publish i.e.
	 * task might need to project 1m rows, but
	 * could publish 1k at a time.
	 */
	int getBatchSizeForTupleProjectorSlaveTask();
	
	/**
	 * Max number of tuples projected by scan operator
	 * that are not yet consumed. Protects memory
	 * consumption.
	 */
	int getScanOperatorUnconsumedTuples();
	
	/**
	 * Number of parallel buckets group by should use.
	 */
	int getGroupByOperatorParallelBuckets();
}
