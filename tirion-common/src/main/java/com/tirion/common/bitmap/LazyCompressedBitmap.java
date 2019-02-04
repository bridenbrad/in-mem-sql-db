/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.bitmap;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import com.tirion.common.io.ByteBufferDataInput;
import com.tirion.common.sequence.buffer.Buffer;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class LazyCompressedBitmap implements Bitmap {
	
	private final Buffer buffer;
	
	public LazyCompressedBitmap(Buffer buffer) {
		super();
		this.buffer = buffer;
	}

	@Override
	public long sizeInBytes() {
		return buffer.sizeInBytes() + 8;
	}

	@Override
	public Kind getKind() {
		return Kind.EWAH;
	}

	/**
	 * Should not be used for peformance reasons.
	 */
	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Should not be used for peformance reasons.
	 */
	@Override
	public boolean isFull() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Should not be used for peformance reasons.
	 */
	@Override
	public boolean isSet(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Should not be used for peformance reasons.
	 */
	@Override
	public void set(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public EWAHCompressedBitmap getUnderlying() {
		return buildUnderlying();
	}
	
	private EWAHCompressedBitmap buildUnderlying() {
		try {
			EWAHCompressedBitmap ewah = new EWAHCompressedBitmap();
			ewah.deserialize(new ByteBufferDataInput(buffer.getPosition(), (ByteBuffer) buffer.getUnderlying()));
			return ewah;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
