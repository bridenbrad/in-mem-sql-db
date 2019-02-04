/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.sql.ast.common.FieldRef;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class SimpleFields implements Fields {
	
	@JsonProperty
	private final List<Field> fields = new ArrayList<Field>();
	
	public SimpleFields() {
		this(new ArrayList<Field>());
	}
	
	public SimpleFields(Entity entity) {
		this(entity.getFields());
	}
	
	public SimpleFields(List<Field> fields) {
//		if(fields != null) {			
			for(Field field : fields) {
				append(field);
			}
//		}
	}
	
	
	public void append(Field field) {
		if(fieldExists(field.getOwner(), field.getName())) {
			throw new IllegalStateException("Field already exists '" + field.getOwner() + "." + field.getName() + "'");
		}
		fields.add(field.cloneMe(field.getOwner(), fields.size()));
	}
	
	public void append(List<Field> fields) {
		for(Field field : fields) {
			append(field);
		}
	}

	@Override
	public int getFieldCount() {
		return fields.size();
	}

	@Override
	public boolean fieldExists(String fieldName) {
		return getFieldInternal(null, fieldName) != null;
	}

	@Override
	public Field getField(String fieldName) {
		return getFieldInternal(null, fieldName);
	}

	@Override
	public List<Field> getFields() {
		return fields;
	}

	public Field getField(int index) {
		return fields.get(index);
	}

	@Override
	public boolean fieldExists(String tableName, String fieldName) {
		return getFieldInternal(tableName, fieldName) != null;
	}

	@Override
	public Field getField(String tableName, String fieldName) {
		Field result = getFieldInternal(tableName, fieldName);
		if(result == null) {
			throw new IllegalStateException("Unable to find field for '" + tableName + "." + fieldName + "'");
		}
		return result;
	}
	
	@Override
	public boolean fieldExists(FieldRef fieldRef) {
		return fieldExists(fieldRef.getOwner(), fieldRef.getName());
	}

	@Override
	public Field getField(FieldRef fieldRef) {
		return getField(fieldRef.getOwner(), fieldRef.getName());
	}

	@Override
	public Fields merge(Fields fields) {
		SimpleFields result = new SimpleFields(getFields());
		result.append(fields.getFields());
		return result;
	}
	
	/**
	 * @param tableName if null only field names will be used
	 */
	private Field getFieldInternal(String tableName, String fieldName) {
		Field result = null;
		for(Field field : fields) {
			if(!field.getName().equalsIgnoreCase(fieldName)) {
				continue;
			}
			if(tableName != null) {
				if(!field.getOwner().equalsIgnoreCase(tableName)) {
					continue;
				}
			}
			if(result != null) {
				throw new IllegalStateException("Duplicate field '" + field.toString() + "'");
			}
			result = field;
		}
		return result;
	}
}
