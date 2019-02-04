/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.bitmap;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.ListAware;

/**
 * List of {@link Bitmap}s.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Bitmaps implements ListAware<Bitmap> {

	private final List<Bitmap> list;

	public Bitmaps() {
		super();
		list = new ArrayList<Bitmap>();
	}
	
	public Bitmaps(int size) {
		super();
		list = new ArrayList<Bitmap>(size);
	}
	
	public void nullFill(int count) {
		for (int i = 0; i < count; i++) {
			list.add(null);
		}
	}

	@Override
	public void append(List<Bitmap> values) {
		list.addAll(values);
	}

	@Override
	public void append(Bitmap bitmap) {
		list.add(bitmap);
	}
	
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}
	
	@Override
	public List<Bitmap> get() {
		return list;
	}
	
	@Override
	public boolean has(int index) {
		return index < list.size();
	}
	
	@Override
	public Bitmap get(int index) {
		return list.get(index);
	}
	
	public void set(int index, Bitmap bitmap) {
		list.set(index, bitmap);
	}
	
	public Bitmap getLast() {
		return list.get(list.size() - 1);
	}
}
