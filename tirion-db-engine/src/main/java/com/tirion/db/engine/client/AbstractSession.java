/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.engine.client;

import java.util.concurrent.atomic.AtomicLong;

import com.tirion.db.common.Config;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractSession implements Session {
	
	private static final long serialVersionUID = -7510825205568557891L;
	
	private final long id;
	private final AtomicLong requestIdSeed;
	private final Config config;
	
	public AbstractSession(long id) {
		this.id = id;
		requestIdSeed = new AtomicLong(0);
		config = new Config();
	}
	
	public final long nextRequestId() {
		return requestIdSeed.getAndIncrement();
	}
	
	@Override
	public final long getId() {
		return id;
	}

	@Override
	public final Config getConfig() {
		return config;
	}
}
