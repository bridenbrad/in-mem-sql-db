/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.extractor;

import java.nio.ByteBuffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class BufferExtractor implements Extractor {

	protected final ByteBuffer buffer;
	
	public BufferExtractor(ByteBuffer buffer) {
		this.buffer = buffer;
	}
	
	public abstract Object extract(int index);
	
	public static final class ByteBufferExtractor extends BufferExtractor {

		public ByteBufferExtractor(ByteBuffer buffer) {
			super(buffer);
		}

		@Override
		public Object extract(int index) {
			return buffer.get(index);
		}
	}
	
	public static final class ShortBufferExtractor extends BufferExtractor {

		public ShortBufferExtractor(ByteBuffer buffer) {
			super(buffer);
		}

		@Override
		public Object extract(int index) {
			return buffer.getShort(index);
		}
	}
	
	public static final class IntegerBufferExtractor extends BufferExtractor {

		public IntegerBufferExtractor(ByteBuffer buffer) {
			super(buffer);
		}

		@Override
		public Object extract(int index) {
			return buffer.getInt(index);
		}
	}
	
	public static final class LongBufferExtractor extends BufferExtractor {

		public LongBufferExtractor(ByteBuffer buffer) {
			super(buffer);
		}

		@Override
		public Object extract(int index) {
			return buffer.getLong(index);
		}
	}
	
	public static final class FloatBufferExtractor extends BufferExtractor {

		public FloatBufferExtractor(ByteBuffer buffer) {
			super(buffer);
		}

		@Override
		public Object extract(int index) {
			return buffer.getFloat(index);
		}
	}

	public static final class DoubleBufferExtractor extends BufferExtractor {

		public DoubleBufferExtractor(ByteBuffer buffer) {
			super(buffer);
		}

		@Override
		public Object extract(int index) {
			return buffer.getDouble(index);
		}
	}
}
