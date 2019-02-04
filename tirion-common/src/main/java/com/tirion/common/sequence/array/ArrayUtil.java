/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.sequence.array;

import java.nio.ByteBuffer;

import com.tirion.common.Util;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class ArrayUtil {
	
	public static byte[] shortToByteArray(short[] array) {
		byte[] result = new byte[array.length * 2];
		ByteBuffer buffer = ByteBuffer.wrap(result);
		for (int i = 0; i < array.length; i++) {
			buffer.putShort(array[i]);
		}
		return result;
	}
	
	public static byte[] intToByteArray(int[] array) {
		byte[] result = new byte[array.length * 4];
		ByteBuffer buffer = ByteBuffer.wrap(result);
		for (int i = 0; i < array.length; i++) {
			buffer.putInt(array[i]);
		}
		return result;
	}
	
	public static byte[] longToByteArray(long[] array) {
		byte[] result = new byte[array.length * 8];
		ByteBuffer buffer = ByteBuffer.wrap(result);
		for (int i = 0; i < array.length; i++) {
			buffer.putLong(array[i]);
		}
		return result;
	}
	
	public static byte[] floatToByteArray(float[] array) {
		byte[] result = new byte[array.length * 4];
		ByteBuffer buffer = ByteBuffer.wrap(result);
		for (int i = 0; i < array.length; i++) {
			buffer.putFloat(array[i]);
		}
		return result;
	}
	
	public static byte[] doubleToByteArray(double[] array) {
		byte[] result = new byte[array.length * 8];
		ByteBuffer buffer = ByteBuffer.wrap(result);
		for (int i = 0; i < array.length; i++) {
			buffer.putDouble(array[i]);
		}
		return result;
	}

	public static short[] byteToShortArray(byte[] array) {
		Util.assertTrue(array.length % 2 == 0, "Input array does not have correct multiple");
		
		ByteBuffer buffer = ByteBuffer.wrap(array);
		short[] result = new short[array.length / 2];
		for (int i = 0; i < result.length; i++) {
			result[i] = buffer.getShort();
		}
		return result;
	}
	
	public static int[] byteToIntArray(byte[] array) {
		Util.assertTrue(array.length % 4 == 0, "Input array does not have correct multiple");
		
		ByteBuffer buffer = ByteBuffer.wrap(array);
		int[] result = new int[array.length / 4];
		for (int i = 0; i < result.length; i++) {
			result[i] = buffer.getInt();
		}
		return result;
	}

	public static long[] byteToLongArray(byte[] array) {
		Util.assertTrue(array.length % 8 == 0, "Input array does not have correct multiple");
		
		ByteBuffer buffer = ByteBuffer.wrap(array);
		long[] result = new long[array.length / 8];
		for (int i = 0; i < result.length; i++) {
			result[i] = buffer.getLong();
		}
		return result;
	}
	
	public static float[] byteToFloatArray(byte[] array) {
		Util.assertTrue(array.length % 4 == 0, "Input array does not have correct multiple");
		
		ByteBuffer buffer = ByteBuffer.wrap(array);
		float[] result = new float[array.length / 4];
		for (int i = 0; i < result.length; i++) {
			result[i] = buffer.getFloat();
		}
		return result;
	}

	public static double[] byteToDoubleArray(byte[] array) {
		Util.assertTrue(array.length % 8 == 0, "Input array does not have correct multiple");
		
		ByteBuffer buffer = ByteBuffer.wrap(array);
		double[] result = new double[array.length / 8];
		for (int i = 0; i < result.length; i++) {
			result[i] = buffer.getDouble();
		}
		return result;
	}
	
	private ArrayUtil() {
	}
}
