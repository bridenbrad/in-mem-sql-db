/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.column;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.type.Type;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public abstract class AbstractColumn implements Column {

	@JsonProperty
	private final String name;
	@JsonProperty
	private final Type type;
	
	public AbstractColumn(String name, Type type) {
		super();
		this.name = name;
		this.type = type;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final Type getType() {
		return type;
	}
	
	protected final int getAbstractSize() {
		return 8 + 8 + name.length();
	}
}
