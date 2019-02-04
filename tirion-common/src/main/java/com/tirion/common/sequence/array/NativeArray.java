/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.sequence.array;

import java.nio.ByteBuffer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tirion.common.NotYetImplementedException;
import com.tirion.common.sequence.AbstractSequence;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class NativeArray extends AbstractSequence implements com.tirion.common.sequence.array.Array {

	private final Object array;

	public NativeArray(Spec spec, Object array) {
		super(spec);
		this.array = array;
	}
	
	@Override
	public int size() {
		if(isCompressed()) {
			return ((byte[])array).length;
		} else {
			return getCount() * getType().getWidth();
		}
	}

	@JsonProperty
	@Override
	public Kind getKind() {
		return Kind.ARRAY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getUnderlying() {
		return (T) array;
	}

	@Override
	public long sizeInBytes() {
		throw new NotYetImplementedException();
	}

	@Override
	public void writeTo(Buffer buffer) {
		final ByteBuffer underlying = buffer.getUnderlying();
		if(isCompressed()) {
			int position = buffer.getPosition();
			byte[] arr = (byte[]) array;
			for (int i = 0; i < arr.length; i++) {
				underlying.put(position, arr[i]);
				position += Type.BYTE.getWidth();
			}
		} else {
			switch (getType()) {
				case BYTE:
					{
						int position = buffer.getPosition();
						byte[] arr = (byte[]) array;
						for (int i = 0; i < arr.length; i++) {
							underlying.put(position, arr[i]);
							position += Type.BYTE.getWidth();
						}
					}
					break;
				case SHORT:
					{
						int position = buffer.getPosition();
						short[] arr = (short[]) array;
						for (int i = 0; i < arr.length; i++) {
							underlying.putShort(position, arr[i]);
							position += Type.SHORT.getWidth();
						}
					}
					break;
				case INT:
					{
						int position = buffer.getPosition();
						int[] arr = (int[]) array;
						for (int i = 0; i < arr.length; i++) {
							underlying.putInt(position, arr[i]);
							position += Type.INT.getWidth();
						}	
					}
					break;
				case LONG:
					{
						int position = buffer.getPosition();
						long[] arr = (long[]) array;
						for (int i = 0; i < arr.length; i++) {
							underlying.putLong(position, arr[i]);
							position += Type.LONG.getWidth();
						}	
					}
					break;
				case FLOAT:
					{
						int position = buffer.getPosition();
						float[] arr = (float[]) array;
						for (int i = 0; i < arr.length; i++) {
							underlying.putFloat(position, arr[i]);
							position += Type.FLOAT.getWidth();
						}	
					}
					break;
				case DOUBLE:
					{
						int position = buffer.getPosition();
						double[] arr = (double[]) array;
						for (int i = 0; i < arr.length; i++) {
							underlying.putDouble(position, arr[i]);
							position += Type.DOUBLE.getWidth();
						}	
					}
					break;
				default:
					throw new IllegalArgumentException("Type '" + getType() + "' is not supported yet");
			}	
		}
	}
}
