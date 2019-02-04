/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.allocator;

import java.util.HashMap;
import java.util.Map;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.tirion.common.Pair;
import com.tirion.common.metrics.Metrics;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractStoragePool implements StoragePool {

	protected final Type[] allowedTypes = new Type[] {
		Type.BYTE, Type.SHORT, Type.INT, Type.LONG, Type.FLOAT, Type.DOUBLE
	};
	
	private Counter count;
	private Counter volume;
	private Histogram size;
	// first is instance count, second is byte count
	private Map<Type, Pair<Counter, Counter>> map = new HashMap<Type, Pair<Counter, Counter>>();
	
	@Override
	public void init() {
		count = Metrics.get().counter(MetricRegistry.name("allocator", "total-count"));
		volume = Metrics.get().counter(MetricRegistry.name("allocator", "total-volume"));
		size = Metrics.get().histogram(MetricRegistry.name("allocator", "size"));
		for(Type type : allowedTypes) {
			Counter count = Metrics.get().counter(MetricRegistry.name("allocator", type.toString().toLowerCase() + "-count"));
			Counter volume = Metrics.get().counter(MetricRegistry.name("allocator", type.toString().toLowerCase() + "-volume"));
			map.put(type, new Pair<Counter, Counter>(count, volume));
		}
	}

	@Override
	public void shutdown() {	
	}

	@Override
	public final Buffer allocate(Spec spec) {
		for (int i = 0; i < allowedTypes.length; i++) {
			if(allowedTypes[i] == spec.getType()) {
				Buffer buffer = allocateInternal(spec);
				
				long bytes = spec.getCount() * spec.getType().getWidth();
				this.size.update(bytes);
				this.volume.inc(bytes);
				this.count.inc();
				
				Pair<Counter, Counter> pair = map.get(spec.getType());
				pair.getFirst().inc();
				pair.getSecond().inc(spec.getType().getWidth() * spec.getCount());
				return buffer;				
			}
		}
		throw new IllegalArgumentException("Illegal type '" + spec.getType() + "' for storage pool");
	}

	protected abstract Buffer allocateInternal(Spec spec);
}
