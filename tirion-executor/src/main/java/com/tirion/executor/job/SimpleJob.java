/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor.job;

import java.util.concurrent.atomic.AtomicLong;

public final class SimpleJob implements Job {

	private final long id;
	private final AtomicLong taskId;

	public SimpleJob(long id) {
		super();
		this.id = id;
		taskId = new AtomicLong();
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public long nextTaskId() {
		return taskId.incrementAndGet();
	}
}
