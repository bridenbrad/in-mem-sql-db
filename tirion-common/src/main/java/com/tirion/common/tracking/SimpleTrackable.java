/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.tracking;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO atomics may decrease performance, benchmark/monitor
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleTrackable<T> implements Trackable<T> {

	private final T value;
	private final AtomicLong count;
	private final AtomicLong lastAccessTime;
	
	public SimpleTrackable(T value) {
		super();
		this.value = value;
		this.count = new AtomicLong(0);
		this.lastAccessTime = new AtomicLong(0);
	}

	@Override
	public T get() {
		count.getAndIncrement();
		lastAccessTime.set(System.currentTimeMillis());
		return value;
	}

	@Override
	public long getAccessCount() {
		return count.get();
	}

	@Override
	public long getLastAccessTime() {
		return lastAccessTime.get();
	}
}
