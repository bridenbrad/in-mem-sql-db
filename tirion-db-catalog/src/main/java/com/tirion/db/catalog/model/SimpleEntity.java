/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Describes single relation, such as physical table or derived
 * tuple flowing thru query operator tree.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleEntity implements Serializable, Entity {

	// for RMI/JDBC reasons
	private static final long serialVersionUID = -8516894383612109958L;
	
	@JsonProperty
	private final String name;
	@JsonProperty
	private final EntityType type;
	@JsonProperty
	private final List<Field> fields;

	/**
	 * Assumes large type.
	 */
	public SimpleEntity(String name) {
		this(name, EntityType.LARGE);
	}
	
	public SimpleEntity(String name, EntityType type) {
		this.name = name.toLowerCase();
		this.type = type;
		fields = new ArrayList<Field>();
	}

	@Override
	public EntityType getType() {
		return type;
	}

	/**
	 * Will clone given field and inject new owner and index.
	 */
	public void append(Field field) {
		if(hasField(field.getName())) {
			throw new IllegalArgumentException("Duplicate field '" + field.getName() + "' for table '" + name + "'");
		}
		fields.add(field.cloneMe(getName(), fields.size()));
	}
	
	public void append(List<Field> fields) {
		for(Field field : fields) {
			append(field);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int fieldCount() {
		return fields.size();
	}
	
	@Override
	public boolean hasField(String fieldName) {
		return getFieldInternal(fieldName) != null;
	}
	
	@Override
	public Field getField(String fieldName) {
		Field field = getFieldInternal(fieldName);
		if(field == null) {			
			throw new IllegalArgumentException("Field '" + fieldName + "' does not exist within entity '" + getName() + "'");
		}
		return field;
	}

	@Override
	public List<Field> getFields() {
		return fields;
	}
	
	@Override
	public Field getField(int index) {
		return fields.get(index);
	}
	
	@Override
	public Entity cloneMe(String newName) {
		SimpleEntity result = new SimpleEntity(newName, getType());
		result.append(getFields());
		return result;
	}

	private Field getFieldInternal(String fieldName) {
		for(Field field : fields) {
			if(field.getName().equalsIgnoreCase(fieldName)) {
				return field;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "SimpleEntity [name=" + name + ", type=" + type + ", fields="
				+ fields + "]";
	}
}
