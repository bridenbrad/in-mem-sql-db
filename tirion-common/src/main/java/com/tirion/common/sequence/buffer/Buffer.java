/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.sequence.buffer;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.NotYetImplementedException;
import com.tirion.common.sequence.AbstractSequence;
import com.tirion.common.sequence.Spec;
import com.tirion.common.type.Type;

/**
 * Wrapper around {@link ByteBuffer} backed by {@link MappedByteBuffer}.
 * Contains null bitmap, count, position & {@link Type}. Count is number
 * of values of given type (not count of bytes).<p>
 * 
 * Underlying {@link ByteBuffer} should be queried in indexed mode
 * only i.e. no relative lookups.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Buffer extends AbstractSequence {

	@JsonProperty
	private final int position;
	private final int size;
	private final java.nio.ByteBuffer underlying;
	
	public Buffer(Spec spec, int position, int size, ByteBuffer underlying) {
		super(spec);
		if(!spec.isCompressed() && (size != (spec.getCount() * spec.getType().getWidth()))) {
			throw new IllegalArgumentException();
		}
		this.underlying = underlying;
		this.position = position;
		this.size = size;
	}
	
	@Override
	public int size() {
		return size;
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.BUFFER;
	}

	/**
	 * Position within underlying {@link ByteBuffer}.
	 */
	public int getPosition() {
		return position;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getUnderlying() {
		return (T) underlying;
	}

	@Override
	public long sizeInBytes() {
//		return 28 + (hasNullBitmap() ? getNullBitmap().sizeInBytes() : 0) + (count * type.getWidth());
		throw new NotYetImplementedException();
	}
}
