/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compressor;

import com.tirion.common.Lifecycle;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Compressor extends Lifecycle {
	
	public static enum Kind {
		SNAPPY,
		ZIP,;
		
		public static Kind parseFrom(String value) {
			for(Kind kind : values()) {
				if(kind.name().equalsIgnoreCase(value)) {
					return kind;
				}
			}
			throw new IllegalArgumentException("Illegal compressor type '" + value + "'");
		}
	}
	
	Kind getKind();
	
	/**
	 * Returns byte array, and includes original null bitmap, it any. 
	 * Returns null in case compression is useless i.e. no space saving 
	 * achieved.
	 */
	Array compress(Array array);

	byte[] compressByteArray(byte[] array);
	
	byte[] compressShortArray(short[] array);
	
	byte[] compressIntArray(int[] array);
	
	byte[] compressLongArray(long[] array);

	byte[] compressFloatArray(float[] array);
	
	byte[] compressDoubleArray(double[] array);
	
	/**
	 * Uncompress given byte[] into target type.
	 */
	Object uncompress(byte[] array, Type type);
	
	/**
	 * Uncompress given byte[] into target type.
	 */
	Array uncompress(Array array);
	
	byte[] uncompressByteArray(byte[] data);
	
	short[] uncompressShortArray(byte[] data);
	
	int[] uncompressIntArray(byte[] data);
	
	long[] uncompressLongArray(byte[] data);
	
	float[] uncompressFloatArray(byte[] data);
	
	double[] uncompressDoubleArray(byte[] data);
}

