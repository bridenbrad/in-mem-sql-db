/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compressor;

import java.io.IOException;

import org.xerial.snappy.Snappy;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SnappyCompressor extends AbstractCompressor {

	@Override
	public Kind getKind() {
		return Kind.SNAPPY;
	}

	@Override
	public byte[] compressByteArray(byte[] array) {
		try {
			return Snappy.compress(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] compressShortArray(short[] array) {
		try {
			return Snappy.compress(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] compressIntArray(int[] array) {
		try {
			return Snappy.compress(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] compressLongArray(long[] array) {
		try {
			return Snappy.compress(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] compressFloatArray(float[] array) {
		try {
			return Snappy.compress(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] compressDoubleArray(double[] array) {
		try {
			return Snappy.compress(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] uncompressByteArray(byte[] array) {
		try {
			return Snappy.uncompress(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public short[] uncompressShortArray(byte[] array) {
		try {
			return Snappy.uncompressShortArray(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int[] uncompressIntArray(byte[] array) {
		try {
			return Snappy.uncompressIntArray(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long[] uncompressLongArray(byte[] array) {
		try {
			return Snappy.uncompressLongArray(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public float[] uncompressFloatArray(byte[] array) {
		try {
			return Snappy.uncompressFloatArray(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public double[] uncompressDoubleArray(byte[] array) {
		try {
			return Snappy.uncompressDoubleArray(array);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
