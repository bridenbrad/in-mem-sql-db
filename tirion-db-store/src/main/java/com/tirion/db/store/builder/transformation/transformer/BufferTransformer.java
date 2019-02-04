/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.transformer;

import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.Sequence;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.store.allocator.Allocator;
import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.slice.StatsSequenceSlice;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class BufferTransformer extends AbstractTransformer {

	private final Allocator allocator;
	private final boolean[] offheap;
	
	public BufferTransformer(Entity entity, Allocator allocator, Runtime runtime, Job job) {
		super(entity, runtime, job);
		this.allocator = allocator;
		offheap = getOffheapFlags(entity);
	}

	@Override
	public Slice transform(Slice slice) {
		return transformInternal((SequenceSlice)slice);
	}

	private SequenceSlice transformInternal(SequenceSlice slice) {
		Sequence[] sequences = slice.getSequences();
		Sequence[] result = new Sequence[sequences.length];
		for (int i = 0; i < sequences.length; i++) {
			Array array = (Array)sequences[i];
			if(offheap[i]) {
				Buffer buffer = allocator.allocate(array.getSpec());
				array.writeTo(buffer);
				result[i] = buffer; 				
			} else {
				result[i] = array;
			}
		}
		if(slice instanceof StatsSequenceSlice) {
			return new StatsSequenceSlice(slice.getId(), slice.getRowCount(), result, ((StatsSequenceSlice)slice).getStats());
		} else {
			return new SequenceSlice(slice.getId(), slice.getRowCount(), result);
		}
	}
	
	private boolean[] getOffheapFlags(Entity entity) {
		boolean[] offheap = new boolean[entity.fieldCount()];
		for (int i = 0; i < offheap.length; i++) {
			if(entity.getField(i).getOptions().isOffHeap()) {
				offheap[i] = true;
			}
		}
		return offheap;
	}
}
