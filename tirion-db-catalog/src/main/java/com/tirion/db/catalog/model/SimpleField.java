/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.options.Options;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleField implements Field {

	private static final long serialVersionUID = 6538005463972053956L;
	
	@JsonProperty
	private final String name;
	@JsonProperty
	private final String owner;
	@JsonProperty
	private final Type type;
	@JsonProperty
	private final int index;
	@JsonProperty
	private final Options options;

	/**
	 * Not nullable & uses default options.
	 */
	public SimpleField(String name, String owner, Type type, int index) {
		this(name, owner, type, index, Options.DEFAULT);
	}

	public SimpleField(String name, String owner, Type type, int index, Options options) {
		if((type == Type.BOOLEAN || type == Type.BYTE) && options.isTokenized()) {
			throw new IllegalArgumentException("Can not tokenize byte or boolean column '" + name + "' in table '" + owner + "'");
		}
		
		this.name = name.toLowerCase();
		this.owner = owner.toLowerCase();
		this.type = type;
		this.index = index;
//		if(!options.isTokenized() && type == Type.STRING) {
//			options = new Options(true, 
//								  options.hasBmIndex(), 
//								  options.hasStats(), 
//								  options.isCompressed(), 
//								  options.isOffHeap(), 
//								  Constants.LONG_DISTINCT_COUNT);
//		}
		this.options = (options == null ? Options.DEFAULT : options);
	}

	@Override
	public boolean hasOwner() {
		return owner != null;
	}

	@Override
	public String getOwner() {
		return owner;
	}

	@Override
	public Options getOptions() {
		return options;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Type getDeclaredType() {
		return type;
	}
	
	@Override
	public Type getNativeType() {
		final Type declaredType = getDeclaredType();
		switch (declaredType) {
			case DATE:
				return Type.SHORT;
			case TIME:
				return Type.INT;
			case TIMESTAMP:
				return Type.LONG;
			case VARCHAR:
				return Type.LONG;
			default:
				return declaredType;
		}
	}
	
	@Override
	public Type getNativeTypeHack() {
		final Type declaredType = getDeclaredType();
		switch (declaredType) {
			case DATE:
				return Type.SHORT;
			case TIME:
				return Type.INT;
			case TIMESTAMP:
				return Type.LONG;
			default:
				return declaredType;
		}
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public Field cloneMe(String newOwner, int newIndex) {
		return new SimpleField(name, newOwner, type, newIndex, options);
	}
	
	@Override
	public boolean areSame(Field rhs) {
		if(hasOwner() != rhs.hasOwner()) { 
			return false;
		}
		if(hasOwner() && !getOwner().equals(rhs.getOwner())) {
			return false;
		}
		return getName().equals(rhs.getName());
	}

	@Override
	public String toString() {
		return "SimpleField [name=" + name + ", owner=" + owner + ", type="
				+ type + ", index=" + index + ", options=" + options + "]";
	}
}
