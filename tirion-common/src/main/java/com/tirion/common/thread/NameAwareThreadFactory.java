/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NameAwareThreadFactory implements ThreadFactory {

	private static final Logger log = LoggerFactory.getLogger(NameAwareThreadFactory.class);
	
	private final String threadNamePrefix;
	private final AtomicLong counter;

	public NameAwareThreadFactory(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
		counter = new AtomicLong(1);
	}

	@Override
	public Thread newThread(Runnable r) {
		final String threadName = threadNamePrefix + "-" + counter.getAndIncrement();
		if(log.isTraceEnabled()) {
			log.trace("Creating new thread '" + threadName + "'");
		}
		return new Thread(r, threadName);
	}
}
