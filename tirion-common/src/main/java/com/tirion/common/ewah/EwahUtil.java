/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.ewah;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.tirion.common.Constants;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class EwahUtil {
	
	public static final EWAHCompressedBitmap ALL_ONES = new EWAHCompressedBitmap(Constants.ROWS_PER_PAGE / 64);
	static {
		for (int i = 0; i < Constants.ROWS_PER_PAGE / 64; i++) {
			ALL_ONES.add(-1L);
		}
	}

	private EwahUtil() {
	}
}
