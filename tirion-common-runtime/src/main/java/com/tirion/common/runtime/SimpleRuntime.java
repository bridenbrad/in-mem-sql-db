/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.runtime;

import com.tirion.common.Configuration;
import com.tirion.compressor.Compressor;
import com.tirion.executor.Executor;
import com.tirion.executor.job.JobFactory;
import com.tirion.pool.Pool;

public final class SimpleRuntime implements Runtime {

	private final Configuration configuration;
	private final Compressor compressor;
	private final Executor executor;
	private final JobFactory jobFactory;
	private final Pool pool;
	
	public SimpleRuntime(Configuration configuration, Compressor compressor,
			Executor executor, JobFactory jobFactory, Pool pool) {
		super();
		this.configuration = configuration;
		this.compressor = compressor;
		this.executor = executor;
		this.jobFactory = jobFactory;
		this.pool = pool;
	}

	@Override
	public Configuration configuration() {
		return configuration;
	}

	@Override
	public Compressor compressor() {
		return compressor;
	}

	@Override
	public Executor executor() {
		return executor;
	}

	@Override
	public JobFactory jobFactory() {
		return jobFactory;
	}

	@Override
	public Pool pool() {
		return pool;
	}
}
