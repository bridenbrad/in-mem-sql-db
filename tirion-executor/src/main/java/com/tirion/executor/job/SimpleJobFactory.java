/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor.job;

public final class SimpleJobFactory implements JobFactory {

	private long id;
	
	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public synchronized Job newJob() {
		return new SimpleJob(id++);
	}
}
