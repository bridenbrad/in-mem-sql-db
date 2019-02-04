/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.demo.nullify;

import java.util.Random;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class PercentNullDecider implements NullDecider {

	private final int percent;
	private final Random random;
	
	public PercentNullDecider(int percent) {
		if(!(0 <= percent && percent <= 100)) {
			throw new IllegalArgumentException("Percent must be between 0 and 100");
		}
		this.percent = percent;
		random = new Random();
	}

	@Override
	public boolean isNull() {
		if(percent == 0) {
			return false;
		}
		int value = random.nextInt(101);
		if(value <= percent) {
			return true;
		}
		return false;
	}
}
