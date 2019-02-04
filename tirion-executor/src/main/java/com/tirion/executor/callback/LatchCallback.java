/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor.callback;

import java.util.concurrent.CountDownLatch;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LatchCallback implements Callback {

	private final CountDownLatch latch;
	
	public LatchCallback(int count) {
		this.latch = new CountDownLatch(count);
	}

	@Override
	public void report() {
		latch.countDown();
	}
	
	public boolean isDone() {
		return latch.getCount() == 0;
	}
	
	public void waitDone() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
