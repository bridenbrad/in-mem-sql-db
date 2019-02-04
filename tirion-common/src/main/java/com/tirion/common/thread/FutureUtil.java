/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class FutureUtil {

	public static void waitAll(List<Future<?>> futures) {
		for(Future<?> future : futures) {
			try {
				future.get();
			} catch (Exception e) {
				throw new RuntimeException("Exception while waiting for multiple futures", e);
			}
		}
	}
	
	public static <T> List<T> getAll(List<Future<T>> futures) {
		try {
			List<T> result = new ArrayList<T>(futures.size());
			for(Future<T> future : futures) {
				result.add(future.get());
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("Exception while getting result of multiple futures", e);
		}
	}
	
	private FutureUtil() {
	}
}
