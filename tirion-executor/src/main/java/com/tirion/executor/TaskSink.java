/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor;

import java.util.List;
import java.util.concurrent.Future;

import com.tirion.executor.task.Task;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TaskSink {

	<T> Future<T> submit(Task<T> task);
	
	<T> List<Future<T>> submit(List<? extends Task<T>> tasks);
}
