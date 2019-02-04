/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.transformer;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.runtime.Runtime;
import com.tirion.common.sequence.Sequence;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.common.Config;
import com.tirion.db.store.builder.parser.BooleanParser;
import com.tirion.db.store.builder.parser.ByteParser;
import com.tirion.db.store.builder.parser.DateParser;
import com.tirion.db.store.builder.parser.DoubleParser;
import com.tirion.db.store.builder.parser.FloatParser;
import com.tirion.db.store.builder.parser.IntegerParser;
import com.tirion.db.store.builder.parser.LongParser;
import com.tirion.db.store.builder.parser.Parser;
import com.tirion.db.store.builder.parser.ShortParser;
import com.tirion.db.store.builder.parser.StringParser;
import com.tirion.db.store.builder.parser.TimeParser;
import com.tirion.db.store.builder.parser.TimestampParser;
import com.tirion.db.store.builder.transformation.slice.SequenceSlice;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.slice.TextMatrixSlice;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ParsingTransformer extends AbstractTransformer {
	
	private final Config config;
	private final Parser[] parsers;

	public ParsingTransformer(Entity entity, Config config, Job job, Runtime runtime) {
		super(entity, runtime, job);
		this.config = config;
		parsers = buildParsers();
	}

	@Override
	public Slice transform(Slice slice) {
		return transformInternal((TextMatrixSlice)slice);
	}

	private Slice transformInternal(TextMatrixSlice slice) {
		Sequence[] sequences = new Sequence[parsers.length];
		String[][] stringMatrix = slice.getMatrix();
		for (int i = 0; i < parsers.length; i++) {
			sequences[i] = parsers[i].parse(stringMatrix, 0, stringMatrix.length, i);
		}
		return new SequenceSlice(slice.getId(), slice.getRowCount(), sequences);
	}
	
	private Parser[] buildParsers() {
		List<Parser> parsers = new ArrayList<Parser>();
		for(Field field : getEntity().getFields()) {
			switch (field.getDeclaredType()) {
				case BOOLEAN:
					parsers.add(new BooleanParser(config.getNullString()));
					break;
				case BYTE:
					parsers.add(new ByteParser(config.getNullString()));
					break;
				case SHORT:
					parsers.add(new ShortParser(config.getNullString()));
					break;
				case DATE:
					parsers.add(new DateParser(config.getNullString(), config.getDatePattern()));
					break;
				case INT:
					parsers.add(new IntegerParser(config.getNullString()));
					break;
				case LONG:
					parsers.add(new LongParser(config.getNullString()));
					break;
				case TIME:
					parsers.add(new TimeParser(config.getNullString(), config.getTimePattern()));
					break;
				case TIMESTAMP:
					parsers.add(new TimestampParser(config.getNullString(), config.getTimestampPattern()));
					break;
				case FLOAT:
					parsers.add(new FloatParser(config.getNullString()));
					break;
				case DOUBLE:
					parsers.add(new DoubleParser(config.getNullString()));
					break;
				case VARCHAR:
					parsers.add(new StringParser(config.getNullString(), getRuntime().pool()));
					break;
				default:
					throw new IllegalArgumentException(field.getDeclaredType().toString());
			}
		}
		return parsers.toArray(new Parser[]{});
	}
}
