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
public final class ByteRange implements Range {

	@JsonProperty
	private final byte low;
	@JsonProperty
	private final byte high;

	public ByteRange(byte low, byte high) {
		super();
		this.low = low;
		this.high = high;
	}
	
	/**
	 * Returns true if given range is subset of this range.
	 */
	public boolean contains(byte lowValue, byte highValue) {
		return low < lowValue && highValue < high;
	}
	
	/**
	 * Number of values between low and high.
	 * Exact value.
	 */
	public byte getDiff() {
		return  (byte) 			    (getHigh() - getLow() - 1);
	}

	public byte getLow() {
		return low;
	}

	public byte getHigh() {
		return high;
	}
	
	@Override
	public Type getType() {
		return Type.BYTE;
	}

	@Override
	public long sizeInBytes() {
		return 1 + 1;
	}

	@Override
	public String toString() {
				return getLow() + ".." + getHigh();
			}
}

