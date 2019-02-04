/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.operator.physical.scan.projector.extractor;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.type.Type;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.BooleanValueExtractor;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.ByteValueExtractor;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.DoubleValueExtractor;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.FloatValueExtractor;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.IntValueExtractor;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.LongValueExtractor;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.ShortValueExtractor;
import com.tirion.db.sql.exec.operator.physical.scan.projector.extractor.AbstractValueExtractor.StringValueExtractor;
import com.tirion.db.sql.exec.tuple.Tuple;
import com.tirion.db.store.page.finder.PageFinder;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ValueExtractors implements ValueExtractor {
	
	public static ValueExtractor newExtractor(Type type, int targeTupleIndex, PageFinder pageFinder, Runtime runtime) {
		switch (type) {
			case BOOLEAN:
				return new BooleanValueExtractor(targeTupleIndex, pageFinder);
			case BYTE:
				return new ByteValueExtractor(targeTupleIndex, pageFinder);
			case DATE:
			case SHORT:
				return new ShortValueExtractor(targeTupleIndex, pageFinder);
			case TIME:
			case INT:
				return new IntValueExtractor(targeTupleIndex, pageFinder);
			case VARCHAR:
				return new StringValueExtractor(targeTupleIndex, pageFinder, runtime);
			case TIMESTAMP:
			case LONG:
				return new LongValueExtractor(targeTupleIndex, pageFinder);
			case FLOAT:
				return new FloatValueExtractor(targeTupleIndex, pageFinder);
			case DOUBLE:
				return new DoubleValueExtractor(targeTupleIndex, pageFinder);
			default:
				throw new NotYetImplementedException(type.toString());
		}
	}

	@JsonProperty
	private final ValueExtractor[] extractors;
	
	public ValueExtractors(ValueExtractor[] extractors) {
		super();
		this.extractors = extractors;
	}

	@Override
	public void exec(long rowId, Tuple tuple) {
		for (int i = 0; i < extractors.length; i++) {
			extractors[i].exec(rowId, tuple);
		}
	}
	
	@Override
	public void exec(List<Long> rowIds, List<Tuple> tuples) {
		for (int i = 0; i < extractors.length; i++) {
			extractors[i].exec(rowIds, tuples);
		}
	}

	public int getFieldCount() {
		return extractors.length;
	}
}
