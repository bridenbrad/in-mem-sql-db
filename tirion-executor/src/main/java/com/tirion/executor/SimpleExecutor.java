/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.tirion.common.metrics.Metrics;
import com.tirion.common.thread.NameAwareThreadFactory;
import com.tirion.executor.task.Task;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleExecutor implements Executor {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleExecutor.class);

	private int poolScaleFactor;
	
	private ExecutorService underlying;
	
	private Counter submissionCount;
	private Counter submissionCountBulk;
	private Counter submissionTotal;
	
	public void setPoolScaleFactor(int poolScaleFactor) {
		this.poolScaleFactor = poolScaleFactor;
	}

	@Override
	public void init() {
		final int coreCount = Runtime.getRuntime().availableProcessors();
		log.info("Auto-detected " + coreCount + " cores available to this JVM");
		final int maxThreadCount = coreCount * poolScaleFactor;
		log.info("Max thread count set to " + maxThreadCount);
		
//		underlying = Executors.newCachedThreadPool(new NameAwareThreadFactory("tirion-db-worker"));
		underlying = new ThreadPoolExecutor(
					coreCount, // core thread pool size
				    maxThreadCount, // maximum thread pool size
				    5, // time to wait before resizing pool
				    TimeUnit.MINUTES, 
				    new ArrayBlockingQueue<Runnable>(maxThreadCount, true),
				    new NameAwareThreadFactory("tirion-db-worker"));
		
		submissionCount = Metrics.get().counter(MetricRegistry.name("executor", "submission-count"));
		submissionCountBulk = Metrics.get().counter(MetricRegistry.name("executor", "submission-count-bulk"));
		submissionTotal = Metrics.get().counter(MetricRegistry.name("executor", "submission-total"));
	}

	@Override
	public void shutdown() {
		underlying.shutdownNow();
	}

	@Override
	public synchronized <T> Future<T> submit(Task<T> task) {
		submissionCount.inc();
		submissionTotal.inc();
		return underlying.submit(task);
	}
	

	@Override
	public synchronized <T> List<Future<T>> submit(List<? extends Task<T>> tasks) {
		try {
			List<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
			for(Task<T> task : tasks) {
				futures.add(underlying.submit(task));
			}
			submissionCountBulk.inc();
			submissionTotal.inc(tasks.size());
			return futures;
		} catch (Exception e) {
			throw new RuntimeException("Exception while submitting multiple user plane tasks", e);
		}
	}
}
