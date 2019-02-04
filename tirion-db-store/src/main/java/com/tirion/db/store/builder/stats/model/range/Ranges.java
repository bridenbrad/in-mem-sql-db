/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.stats.model.range;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.ListAware;
import com.tirion.common.SizeAware;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Ranges implements SizeAware, ListAware<Range> {

	private final Type type;
	private final List<Range> ranges;

	public Ranges(Type type) {
		super();
		this.type = type;
		ranges = new ArrayList<Range>();
	}
	
	@Override
	public void append(List<Range> values) {
		for(Range range : ranges) {
			append(range);
		}
	}
	
	public void appenda(List<? extends Range> values) {
		for(Range range : ranges) {
			append(range);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return ranges.isEmpty();
	}

	@Override
	public Range get(int index) {
		return ranges.get(index);
	}

	@Override
	public boolean has(int index) {
		return index < ranges.size();
	}

	@Override
	public void append(Range range) {
		ranges.add(range);
	}

	@Override
	public int getCount() {
		return ranges.size();
	}
	
	@Override
	public List<Range> get() {
		return ranges;
	}

	public Type getType() {
		return type;
	}
	
	@Override
	public long sizeInBytes() {
		int rangesSize = 0;
		for(Range range : ranges) {
			rangesSize += range.sizeInBytes();
		}
		return 8 + rangesSize + ranges.size() * 8;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for(Range range : ranges) {
			buffer.append("(").append(range.toString()).append(")");
		}
		return buffer.toString();
	}
}
