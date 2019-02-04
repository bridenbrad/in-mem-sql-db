/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.bitmap;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class ArrayBackedBitmap implements Bitmap {

	private final boolean[] array;

	public ArrayBackedBitmap(boolean[] array) {
		this.array = array;
	}
	
	@Override
	public Kind getKind() {
		return Kind.ARRAY;
	}

	@Override
	public long sizeInBytes() {
		return 8 + array.length;
	}
	
	@Override
	public void set(int index) {
		if(array == null) {
			throw new UnsupportedOperationException();
		}
		array[index] = true;
	}

	@Override
	public boolean isSet(int index) {
		return array != null && array[index];
	}

	@Override
	public boolean[] getUnderlying() {
		return array;
	}

	@Override
	public boolean isEmpty() {
		if(array == null) {
			return true;
		}
		for (int i = 0; i < array.length; i++) {
			if(array[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isFull() {
		for (int i = 0; i < array.length; i++) {
			if(!array[i]) {
				return false;
			}
		}
		return true;
	}
}
