/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor.task;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Task<V> extends Callable<V> {
	
	/**
	 * Job may have multiple tasks.
	 */
	long getJobId();

	long getTaskId();
	
	long getCreateTime();
	
	long getStartTime();
	
	long getEndTime();
	
	long getDuration(TimeUnit timeUnit);
}
