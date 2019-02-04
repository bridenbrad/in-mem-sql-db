/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor.callback;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class DelegatingCallback implements Callback {

	private final List<Callback> callbacks = new CopyOnWriteArrayList<Callback>();
	
	public void append(Callback callback) {
		callbacks.add(callback);
	}
	
	@Override
	public void report() {
		for(Callback callback : callbacks) {
			callback.report();
		}
	}

	@Override
	public boolean isDone() {
		for(Callback callback : callbacks) {
			if(!callback.isDone()) {
				return false;
			}
		}
		return true;
	}
}
