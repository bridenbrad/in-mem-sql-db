/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.set;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleSmartSet<T> implements SmartSet<T> {

	private final Set<T> set = new LinkedHashSet<T>();
	
	@Override
	public Set<T> get() {
		return set;
	}

	@Override
	public void add(T value) {
		if(!set.contains(value)) {
			set.add(value);
		}
	}

	@Override
	public void add(Set<T> set) {
		for(T value : set) {
			add(value);
		}
	}

	@Override
	public void add(SmartSet<T> set) {
		add(set.get());
	}

	@Override
	public void add(List<T> list) {
		for(T value : list) {
			add(value);
		}
	}
}
