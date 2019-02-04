/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compressor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.tirion.common.NotYetImplementedException;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.array.NativeArray;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractCompressor implements Compressor {

	private static final Logger log = LoggerFactory.getLogger(AbstractCompressor.class);
	
	private float goodCompressionRatio = Float.MAX_VALUE;
	private boolean statsOn = false;
	private MetricRegistry registry;
	
	private Counter good;
	private Counter bad;
	private Counter savedSpace;
	
	public void setRegistry(MetricRegistry registry) {
		this.registry = registry;
	}

	public final void setGoodCompressionRatio(float goodCompressionRatio) {
		this.goodCompressionRatio = goodCompressionRatio;
	}
	
	public void setStatsOn(boolean statsOn) {
		this.statsOn = statsOn;
	}

	@Override
	public void init() {		
		log.info("Using '" + goodCompressionRatio + "' as compression ratio threshold");
	
		if(statsOn) {
			good = registry.counter(MetricRegistry.name("compressor", "good"));
			bad = registry.counter(MetricRegistry.name("compressor", "bad"));
			savedSpace = registry.counter(MetricRegistry.name("compressor", "saved-space"));			
		}
	}

	@Override
	public void shutdown() {
	}

	@Override
	public final Array compress(Array array) {
		byte[] result = null;
		switch (array.getType()) {
			case BYTE:
				result = compressByteArray((byte[])array.getUnderlying());
				break;
			case SHORT:
				result = compressShortArray((short[])array.getUnderlying());
				break;
			case INT:
				result = compressIntArray((int[])array.getUnderlying());
				break;
			case LONG:
				result = compressLongArray((long[])array.getUnderlying());
				break;
			case FLOAT:
				result = compressFloatArray((float[])array.getUnderlying());
				break;
			case DOUBLE:
				result = compressDoubleArray((double[])array.getUnderlying());
				break;
			default:
				throw new NotYetImplementedException("Compression for type " + array.getType().toString() + " is not implemented yet");
		}
		
		if(isGoodCompressionRatio(array.size(), result.length)) {
			return new NativeArray(new Spec(array.getType(), array.getCount(), true, array.getNullBitmap()), result);
		} else {
			return null;
		}
	}
	
	@Override
	public final Object uncompress(byte[] array, Type type) {
		switch (type) {
			case BYTE:
				return uncompressByteArray(array);
			case SHORT:
				return uncompressShortArray(array);
			case INT:
				return uncompressIntArray(array);
			case LONG:
				return uncompressLongArray(array);
			case FLOAT:
				return uncompressFloatArray(array);
			case DOUBLE:
				return uncompressDoubleArray(array);
			default:
				throw new NotYetImplementedException(type.toString());
		}
	}

	@Override
	public final Array uncompress(Array array) {
		switch (array.getType()) {
			case BYTE:
				return new NativeArray(new Spec(array.getType(), array.getCount(), false, array.getNullBitmap()), uncompressByteArray((byte[])array.getUnderlying()));
			case SHORT:
				return new NativeArray(new Spec(array.getType(), array.getCount(), false, array.getNullBitmap()), uncompressShortArray((byte[])array.getUnderlying()));
			case INT:
				return new NativeArray(new Spec(array.getType(), array.getCount(), false, array.getNullBitmap()), uncompressIntArray((byte[])array.getUnderlying()));
			case LONG:
				return new NativeArray(new Spec(array.getType(), array.getCount(), false, array.getNullBitmap()), uncompressLongArray((byte[])array.getUnderlying()));
			case FLOAT:
				return new NativeArray(new Spec(array.getType(), array.getCount(), false, array.getNullBitmap()), uncompressFloatArray((byte[])array.getUnderlying()));
			case DOUBLE:
				return new NativeArray(new Spec(array.getType(), array.getCount(), false, array.getNullBitmap()), uncompressDoubleArray((byte[])array.getUnderlying()));
			default:
				throw new NotYetImplementedException("Decompression for type " + array.getType());
		}
	}
	
	protected final boolean isGoodCompressionRatio(int originalSize, int compressedSize) {
		boolean isGood = ((float)compressedSize) / ((float)originalSize) <= goodCompressionRatio;
		if(statsOn) {
			if(isGood) {
				good.inc();
				savedSpace.inc(originalSize - compressedSize);
			}
			if(!isGood) {
				bad.inc();
			}
		}
		return isGood;
	}
}
