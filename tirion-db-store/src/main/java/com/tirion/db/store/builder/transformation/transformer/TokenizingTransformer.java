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
import com.tirion.db.store.builder.tokenizer.Tokenizer;
import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.slice.StatsSequenceSlice;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class TokenizingTransformer extends AbstractTransformer {
	
	private final Tokenizer[] tokenizers;

	public TokenizingTransformer(Entity entity, Tokenizer[] tokenizers, Job job, Runtime runtime) {
		super(entity, runtime, job);
		this.tokenizers = tokenizers;
	}

	@Override
	public Slice transform(Slice slice) {
		return transformInternal((SequenceSlice)slice);
	}

	private SequenceSlice transformInternal(SequenceSlice slice) {
		Sequence[] sequences = slice.getSequences();
		Sequence[] result = new Array[sequences.length];
		for (int i = 0; i < sequences.length; i++) {
			Tokenizer tokenizer = tokenizers[i];
			if(tokenizer != null) {				
				result[i] = tokenizer.tokenize((Array)sequences[i]);
			} else {
				result[i] = sequences[i];
			}
		}
		if(slice instanceof StatsSequenceSlice) {
			return new StatsSequenceSlice(slice.getId(), slice.getRowCount(), result, ((StatsSequenceSlice)slice).getStats());
		} else {
			return new SequenceSlice(slice.getId(), slice.getRowCount(), result);
		}
	}
}
