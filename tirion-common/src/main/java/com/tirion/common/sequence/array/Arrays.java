/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.sequence.array;

import java.util.ArrayList;
import java.util.List;

import com.tirion.common.ListAware;
import com.tirion.common.type.Type;

/**
 * List of {@link Array} objects of same {@link Type}.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Arrays implements ListAware<Array> {

	private final Type type;
	private final List<Array> arrays;

	public Arrays(Type type) {
		super();
		this.type = type;
		arrays = new ArrayList<Array>();
	}

	public Type getType() {
		return type;
	}

	@Override
	public void append(Array array) {
		if(type != array.getType()) {
			throw new IllegalArgumentException("Expected type '" + type + " but found '" + array.getType() + "'");
		}
		arrays.add(array);
	}
	
	@Override
	public void append(List<Array> values) {
		for(Array array : arrays) {
			append(array);
		}
	}

	@Override
	public List<Array> get() {
		return arrays;
	}
	
	@Override
	public Array get(int index) {
		return arrays.get(index);
	}
	
	@Override
	public boolean has(int index) {
		return index < arrays.size();
	}

	@Override
	public int getCount() {
		return arrays.size();
	}
	
	@Override
	public boolean isEmpty() {
		return arrays.isEmpty();
	}
}
