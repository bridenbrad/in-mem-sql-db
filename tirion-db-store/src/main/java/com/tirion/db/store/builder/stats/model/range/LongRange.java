/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.model.range;

import java.text.DecimalFormat;
import com.tirion.common.type.Type;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class was autogenerated. Do not edit manually.
 */
public final class LongRange implements Range {

	@JsonProperty
	private final long low;
	@JsonProperty
	private final long high;

	public LongRange(long low, long high) {
		super();
		this.low = low;
		this.high = high;
	}
	
	/**
	 * Returns true if given range is subset of this range.
	 */
	public boolean contains(long lowValue, long highValue) {
		return low < lowValue && highValue < high;
	}
	
	/**
	 * Number of values between low and high.
	 * Exact value.
	 */
	public long getDiff() {
		return 			    (getHigh() - getLow() - 1);
	}

	public long getLow() {
		return low;
	}

	public long getHigh() {
		return high;
	}
	
	@Override
	public Type getType() {
		return Type.LONG;
	}

	@Override
	public long sizeInBytes() {
		return 8 + 8;
	}

	@Override
	public String toString() {
				return getLow() + ".." + getHigh();
			}
}

