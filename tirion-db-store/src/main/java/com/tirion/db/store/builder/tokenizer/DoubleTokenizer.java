/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.tokenizer;

import java.nio.ByteBuffer;

import com.tirion.common.bitmap.Bitmap;
import com.tirion.common.sequence.Spec;
import com.tirion.common.sequence.array.Array;
import com.tirion.common.sequence.array.NativeArray;
import com.tirion.common.sequence.buffer.Buffer;
import com.tirion.common.type.Type;
import com.tirion.db.store.column.token.TokenMap;

/**
 * This class was autogenerated. Do not edit manually.
 */
public abstract class DoubleTokenizer implements Tokenizer {

	public static final class DoubleToByteTokenizer extends DoubleTokenizer {
		
		private final TokenMap<Byte, Double> tokenMap;

		public DoubleToByteTokenizer(TokenMap<Byte, Double> tokenMap) {
			super();
			this.tokenMap = tokenMap;
		}

		@Override
		public Array tokenize(Array array) {
			final Bitmap nullBitmap = array.getNullBitmap();
			double[] dataArray = (double[]) array.getUnderlying();
			byte[] tokenArray = new byte[dataArray.length];
			for (int i = 0; i < dataArray.length; i++) {
				if(nullBitmap == null || !nullBitmap.isSet(i)) {						
					tokenArray[i] = tokenMap.getTokenForAllocate(dataArray[i]);
				}
			}
			return new NativeArray(new Spec(Type.BYTE, tokenArray.length, false, nullBitmap), tokenArray);
		}

		@Override
		public Array detokenize(Array tokenArray) {
			final Bitmap nullBitmap = tokenArray.getNullBitmap();
			byte[] tokens = (byte[]) tokenArray.getUnderlying();
			double[] data = new double[tokens.length];
			for (int i = 0; i < data.length; i++) {
				if(nullBitmap == null || !nullBitmap.isSet(i)) {								
					data[i] = tokenMap.getValueFor(tokens[i], true);
				}
			}
			return new NativeArray(new Spec(Type.DOUBLE, data.length, false, nullBitmap), data);
		}

		@Override
		public Array detokenize(Buffer buffer) {
			final int count = buffer.getCount();
			final Bitmap nullBitmap = buffer.getNullBitmap();
			ByteBuffer buff = buffer.getUnderlying();
			
			double[] data = new double[buffer.getCount()];
			int position = buffer.getPosition();
			for (int i = 0; i < count; i++) {
				if(nullBitmap == null || !nullBitmap.isSet(i)) {								
					data[i] = tokenMap.getValueFor(buff.get(position), true);
				}
				++position;
			}
			return new NativeArray(new Spec(Type.DOUBLE, data.length, false, nullBitmap), data);
		}
	}
	
	public static final class DoubleToShortTokenizer extends DoubleTokenizer {
		
		private final TokenMap<Short, Double> tokenMap;

		public DoubleToShortTokenizer(TokenMap<Short, Double> tokenMap) {
			super();
			this.tokenMap = tokenMap;
		}

		@Override
		public Array tokenize(Array array) {
			final Bitmap nullBitmap = array.getNullBitmap();
			double[] dataArray = (double[]) array.getUnderlying();
			short[] tokenArray = new short[dataArray.length];
			for (int i = 0; i < dataArray.length; i++) {
				if(nullBitmap == null || !nullBitmap.isSet(i)) {						
					tokenArray[i] = tokenMap.getTokenForAllocate(dataArray[i]);
				}
			}
			return new NativeArray(new Spec(Type.SHORT, tokenArray.length, false, nullBitmap), tokenArray);
		}

		@Override
		public Array detokenize(Array tokenArray) {
			final Bitmap nullBitmap = tokenArray.getNullBitmap();
			short[] tokens = (short[]) tokenArray.getUnderlying();
			double[] data = new double[tokens.length];
			for (int i = 0; i < data.length; i++) {
				if(nullBitmap == null || !nullBitmap.isSet(i)) {								
					data[i] = tokenMap.getValueFor(tokens[i], true	);
				}
			}
			return new NativeArray(new Spec(Type.DOUBLE, data.length, false, nullBitmap), data);
		}
		
		@Override
		public Array detokenize(Buffer buffer) {
			final int count = buffer.getCount();
			final Bitmap nullBitmap = buffer.getNullBitmap();
			ByteBuffer buff = buffer.getUnderlying();
			
			double[] data = new double[buffer.getCount()];
			int position = buffer.getPosition();
			for (int i = 0; i < count; i++) {
				if(nullBitmap == null || !nullBitmap.isSet(i)) {								
					data[i] = tokenMap.getValueFor(buff.getShort(position), true);
				}
				position += 2;
			}
			return new NativeArray(new Spec(Type.DOUBLE, data.length, false, nullBitmap), data);
		}
	}
}