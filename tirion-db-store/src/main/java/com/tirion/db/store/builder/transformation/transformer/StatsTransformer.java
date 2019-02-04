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
import com.tirion.db.catalog.model.Field;
import com.tirion.db.catalog.model.options.Options;
import com.tirion.db.store.builder.stats.Analyzer;
import com.tirion.db.store.builder.stats.AnalyzerFactory;
import com.tirion.db.store.builder.stats.SimpleAnalyzerFactory;
import com.tirion.db.store.builder.stats.DataStats;
import com.tirion.db.store.builder.stats.analyzer.range.RangesConf;
import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.slice.StatsSequenceSlice;
import com.tirion.executor.job.Job;

/**
 * Noop at the moment.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class StatsTransformer extends AbstractTransformer {
	
	private final Analyzer[] analyzers;

	public StatsTransformer(Entity entity, Job job, Runtime runtime) {
		super(entity, runtime, job);
		analyzers = buildAnalyzers(entity);
	}

	@Override
	public Slice transform(Slice slice) {
		SequenceSlice sequenceSlice = (SequenceSlice) slice;
		DataStats[] stats = transformInternal(sequenceSlice.getSequences());
		return new StatsSequenceSlice(slice.getId(), slice.getRowCount(), sequenceSlice.getSequences(), stats);
	}
	
	private DataStats[] transformInternal(Sequence[] sequences) {
		DataStats[] stats = new DataStats[sequences.length];
		for (int i = 0; i < analyzers.length; i++) {
			if(analyzers[i] != null) {
				stats[i] = analyzers[i].analyze((Array)sequences[i]);
			}
		}
		return stats;
	}
	
	private Analyzer[] buildAnalyzers(Entity entity) {
		AnalyzerFactory factory = new SimpleAnalyzerFactory();
		Analyzer[] result = new Analyzer[entity.fieldCount()];
		for (int i = 0; i < result.length; i++) {
			Field field = entity.getField(i);
			Options options = entity.getField(i).getOptions();
			result[i] = factory.newAnalyzer(field.getNativeTypeHack(), 
								options.hasMmStats(), 
								options.hasRangeStats(), 
								RangesConf.DEFAULT);
		}
		return result;
	}
}
