/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.extractor;

import java.nio.ByteBuffer;

import com.tirion.common.extractor.ArrayExtractor.ByteArrayExtractor;
import com.tirion.common.extractor.ArrayExtractor.DoubleArrayExtractor;
import com.tirion.common.extractor.ArrayExtractor.FloatArrayExtractor;
import com.tirion.common.extractor.ArrayExtractor.IntegerArrayExtractor;
import com.tirion.common.extractor.ArrayExtractor.LongArrayExtractor;
import com.tirion.common.extractor.ArrayExtractor.ShortArrayExtractor;
import com.tirion.common.extractor.BufferExtractor.ByteBufferExtractor;
import com.tirion.common.extractor.BufferExtractor.DoubleBufferExtractor;
import com.tirion.common.extractor.BufferExtractor.FloatBufferExtractor;
import com.tirion.common.extractor.BufferExtractor.IntegerBufferExtractor;
import com.tirion.common.extractor.BufferExtractor.LongBufferExtractor;
import com.tirion.common.extractor.BufferExtractor.ShortBufferExtractor;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class ExtractorFactory {

	public static BufferExtractor newBufferExtractor(Type type, ByteBuffer buffer) {
		switch (type) {
			case BYTE:
				return new ByteBufferExtractor(buffer);
			case SHORT:
				return new ShortBufferExtractor(buffer);
			case INT:
				return new IntegerBufferExtractor(buffer);
			case LONG:
				return new LongBufferExtractor(buffer);
			case FLOAT:
				return new FloatBufferExtractor(buffer);
			case DOUBLE:
				return new DoubleBufferExtractor(buffer);
			default:
				throw new IllegalArgumentException("Illegal type '" + type + "'");
		}
	}
	
	public static ArrayExtractor newArrayExtractor(Type type) {
		switch (type) {
			case BYTE:
				return new ByteArrayExtractor();
			case SHORT:
				return new ShortArrayExtractor();
			case INT:
				return new IntegerArrayExtractor();
			case LONG:
				return new LongArrayExtractor();
			case FLOAT:
				return new FloatArrayExtractor();
			case DOUBLE:
				return new DoubleArrayExtractor();
			default:
				throw new IllegalArgumentException("Illegal type '" + type + "'");
		}
	}
	
	private ExtractorFactory() {
	}
}
