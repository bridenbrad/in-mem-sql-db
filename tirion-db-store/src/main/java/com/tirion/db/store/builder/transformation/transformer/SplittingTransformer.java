/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation.transformer;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.runtime.Runtime;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.common.Config;
import com.tirion.db.store.builder.transformation.slice.Slice;
import com.tirion.db.store.builder.transformation.slice.TextLinesSlice;
import com.tirion.db.store.builder.transformation.slice.TextMatrixSlice;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SplittingTransformer extends AbstractTransformer {

	private static final Logger log = LoggerFactory.getLogger(SplittingTransformer.class);
	
	private final String separator;
	private final boolean skipInvalidLines;
	
	public SplittingTransformer(Entity entity, Config config, Job job, Runtime runtime) {
		super(entity, runtime, job);
		this.separator = config.getSeparator();
		this.skipInvalidLines = config.skipInvalidLines();
	}

	@Override
	public Slice transform(Slice slice) {
		return transformInternal((TextLinesSlice)slice);
	}

	private Slice transformInternal(TextLinesSlice slice) {
		final int fieldCount = getEntity().fieldCount();
		
		List<String> rows = slice.getLines();
		List<String[]> matrix = new LinkedList<String[]>();
		for (int i = 0; i < rows.size(); i++) {
			final String text = rows.get(i);
			final String[] array = text.split(separator, -1);
			if(array.length != fieldCount) {
				String message1 = "Entity '" + getEntity().getName() + "' has " + fieldCount + " fields but line number " 
						+ i + " in slice " + slice.getId() + " has " + array.length + " fields.";
				String message2 = "Invalid line was: " + text;
				if(skipInvalidLines) {
					log.warn(message1 + message2);
					continue;
				} else {					
					throw new IllegalArgumentException(message1 + message2);
				}
			}
			matrix.add(array);
		}
		return new TextMatrixSlice(slice.getId(), matrix.size(), matrix.toArray(new String[][]{}));
	}
}
