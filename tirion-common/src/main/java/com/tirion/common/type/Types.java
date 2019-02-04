/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.type;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Types {
	
	private static final Set<Type> NUMERIC_TYPES = new HashSet<Type>();
	static {
		NUMERIC_TYPES.add(Type.BYTE);
		NUMERIC_TYPES.add(Type.SHORT);
		NUMERIC_TYPES.add(Type.INT);
		NUMERIC_TYPES.add(Type.LONG);
		NUMERIC_TYPES.add(Type.FLOAT);
		NUMERIC_TYPES.add(Type.DOUBLE);
	}

	public static boolean isNumeric(Type type) {
		return NUMERIC_TYPES.contains(type);
	}
	
	private final Set<Type> types;

	public Types() {
		super();
		types = new HashSet<Type>();
	}
	
	public void append(Type type) {
		types.add(type);
	}

	public Set<Type> get() {
		return types;
	}
	
	public boolean contains(Type type) {
		return types.contains(type);
	}
}
