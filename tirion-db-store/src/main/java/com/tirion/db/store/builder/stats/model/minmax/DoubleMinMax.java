/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.model.minmax;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class was autogenerated. Do not edit manually.
 */
public final class DoubleMinMax implements MinMax {

	@JsonProperty
	private final double min;
	@JsonProperty
	private final double max;

	public DoubleMinMax(double min, double max) {
		super();
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean isSingleton() {
		return getMin() == getMax();
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
	
	@Override
	public long sizeInBytes() {
		return 8 + 8;
	}

	@Override
	public String toString() {
		return getMin() + ".." + getMax();
	}
}