/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.uuid;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class UuidUtil {

	// FIXME hack-ish
	private static final AtomicLong COUNTER = new AtomicLong(System.currentTimeMillis());
	
	/**
	 * Returns next unique Java identifier.
	 */
	public static String nextJavaIdentifier(String prefix) {
		return prefix + "_" + COUNTER.getAndIncrement();
	}
	
	public static String nextEntityName() {
		return "entity_" + COUNTER.getAndIncrement();
	}
	
	private UuidUtil() {
	}
}
