/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Constants {

	/**
	 * Number of distinct values we can encode in 1 byte.
	 */
	public static final long BYTE_DISTINCT_COUNT = 256;
	
	/**
	 * Number of distinct values we can encode in 2 bytes.
	 */
	public static final long SHORT_DISTINCT_COUNT = 65536;
	
	/**
	 * Number of distinct values we can encode in 4 bytes.
	 */
	public static final long INT_DISTINCT_COUNT = 4294967296L;
	
	public static final long LONG_DISTINCT_COUNT = Long.MAX_VALUE;
	
	/**
	 * Includes milliseconds.
	 */
	public static final long TIME_DISTINCT_COUNT = 1000 * 60 * 60 * 24L;
	
	/**
	 * Roughly 175 years.
	 */
	public static final long DATE_DISTINCT_COUNT = SHORT_DISTINCT_COUNT;

//	public static final int ROWS_PER_PAGE = 100; // for testing...
	public static final int ROWS_PER_PAGE = 65536;
	
	/**
	 * See {@link Analyzer}.
	 */
	public static final int SKIP_RANGE_THRESHOLD = 1000;
	
	/**
	 * See {@link Analyzer}.
	 */
	public static final int MAX_SKIP_RANGES_PER_PAGE = 2;
}
