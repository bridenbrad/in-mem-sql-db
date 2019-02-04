/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.exec.tuple;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.type.Type;

/**
 * Location of the value within the tuple. Location has index 
 * of field relative to other fields and type of the field.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class Location {

	@JsonProperty
	private final int index;
	@JsonProperty
	private final Type type;
	
	public Location(int index, Type type) {
		super();
		this.index = index;
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Location [index=" + index + ", type=" + type + "]";
	}
}
