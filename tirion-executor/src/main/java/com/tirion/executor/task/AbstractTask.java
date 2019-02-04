/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor.task;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.executor.callback.Callback;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractTask<V> implements Task<V> {

	private static final Logger log = LoggerFactory.getLogger(AbstractTask.class);
	
	private final Job job;
	private final long jobId;
	private final long taskId;
	private final long createTime;
	
	private long startTime;
	private long endTime;
	
	private Callback callback;
	
	public AbstractTask(Job job) {
		this.jobId = job.getId();
		this.taskId = job.nextTaskId();
		this.createTime = System.currentTimeMillis();
		this.job = job;
	}
	
	protected final Job getJob() {
		return job;
	}

	public final void setCallback(Callback callback) {
		this.callback = callback;
	}

	@Override
	public final long getJobId() {
		return jobId;
	}

	@Override
	public final long getTaskId() {
		return taskId;
	}
	
	@Override
	public final long getCreateTime() {
		return createTime;
	}

	@Override
	public final long getDuration(TimeUnit timeUnit) {
		return timeUnit.convert(endTime - startTime, TimeUnit.MILLISECONDS);
	}

	@Override
	public final long getStartTime() {
		return startTime;
	}

	@Override
	public final long getEndTime() {
		return endTime;
	}

	@Override
	public final V call() throws Exception {
		try {
			startTime = System.currentTimeMillis();
			V result = callInternal();
			if(callback != null) {			
				callback.report();
			}
			endTime = System.currentTimeMillis();
			if(log.isTraceEnabled()) {
				log.trace("Done with task " + toString());
			}
			return result;
		} catch (Throwable e) {
			log.error("Exception in job " + getJobId() + " and task " + getTaskId(), e);
			throw new RuntimeException(e);
		}
	}
	
	protected abstract V callInternal() throws Exception;

	@Override
	public String toString() {
		return "[jobId=" + jobId + ", taskId=" + taskId
				+ ", createTime=" + createTime + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", duration=" + getDuration(TimeUnit.MILLISECONDS) + " millis]";
	}
}
