/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compressor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.tirion.common.sequence.array.ArrayUtil;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class JdkZipCompressor extends AbstractCompressor {
	
	private static final String ENTRY_NAME = "tirion-entry";
	
	@Override
	public Kind getKind() {
		return Kind.ZIP;
	}

	@Override
	public byte[] compressByteArray(byte[] array) {
		return compressInternal(array);
	}

	@Override
	public byte[] compressShortArray(short[] array) {
		return compressInternal(ArrayUtil.shortToByteArray(array));
	}

	@Override
	public byte[] compressIntArray(int[] array) {
		return compressInternal(ArrayUtil.intToByteArray(array));
	}

	@Override
	public byte[] compressLongArray(long[] array) {
		return compressInternal(ArrayUtil.longToByteArray(array));
	}

	@Override
	public byte[] compressFloatArray(float[] array) {
		return compressInternal(ArrayUtil.floatToByteArray(array));
	}

	@Override
	public byte[] compressDoubleArray(double[] array) {
		return compressInternal(ArrayUtil.doubleToByteArray(array));
	}

	@Override
	public byte[] uncompressByteArray(byte[] data) {
		return uncompressInternal(data);
	}

	@Override
	public short[] uncompressShortArray(byte[] data) {
		return ArrayUtil.byteToShortArray(uncompressInternal(data));
	}

	@Override
	public int[] uncompressIntArray(byte[] data) {
		return ArrayUtil.byteToIntArray(uncompressInternal(data));
	}

	@Override
	public long[] uncompressLongArray(byte[] data) {
		return ArrayUtil.byteToLongArray(uncompressInternal(data));
	}

	@Override
	public float[] uncompressFloatArray(byte[] data) {
		return ArrayUtil.byteToFloatArray(uncompressInternal(data));
	}

	@Override
	public double[] uncompressDoubleArray(byte[] data) {
		return ArrayUtil.byteToDoubleArray(uncompressInternal(data));
	}
	
	private byte[] uncompressInternal(byte[] array) { // clearly not tuned for performance
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();	
			ByteArrayInputStream bais = new ByteArrayInputStream(array);
			ZipInputStream zis = new ZipInputStream(bais);
			zis.getNextEntry();
			while(true) {
				int bite = zis.read();
				if(bite == -1) {
					break;
				}
				baos.write(bite);
			}
			zis.closeEntry();
			zis.close();
			baos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private byte[] compressInternal(byte[] array) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
			ZipEntry entry = new ZipEntry(ENTRY_NAME);
			zos.putNextEntry(entry);
			zos.write(array);
			zos.closeEntry();
			zos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
