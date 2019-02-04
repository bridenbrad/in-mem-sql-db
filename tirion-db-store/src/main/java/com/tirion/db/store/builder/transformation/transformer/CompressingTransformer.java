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
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.slice.StatsSequenceSlice;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class CompressingTransformer extends AbstractTransformer {

	public CompressingTransformer(Entity entity, Job job, Runtime runtime) {
		super(entity, runtime, job);
	}

	@Override
	public Slice transform(Slice slice) {
		return transformInternal((SequenceSlice)slice);
	}

	private Slice transformInternal(SequenceSlice slice) {
		Sequence[] input = slice.getSequences();
		Sequence[] output = new Array[input.length];
		
		for (int i = 0; i < input.length; i++) {
			Array array = (Array) input[i];
			if(shouldBeCompressed(i)) {
				Array compressedArray = getRuntime().compressor().compress((Array)input[i]);				
				if(compressedArray != null) {
					output[i] = compressedArray;
				} else {
					output[i] = array;
				}
			} else {				
				output[i] = array;
			}
		}
		if(slice instanceof StatsSequenceSlice) {
			return new StatsSequenceSlice(slice.getId(), slice.getRowCount(), output, ((StatsSequenceSlice)slice).getStats());
		} else {
			return new SequenceSlice(slice.getId(), slice.getRowCount(), output);
		}
	}
}
