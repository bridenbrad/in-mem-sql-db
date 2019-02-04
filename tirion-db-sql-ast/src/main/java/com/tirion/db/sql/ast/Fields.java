/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast;

import java.util.List;

import com.tirion.db.catalog.model.Field;
import com.tirion.db.sql.ast.common.FieldRef;

/**
 * Container for {@link Field}'s from mutiple underlying 
 * entities. Relies on field's owner as indicator which field
 * is owned by which underlying entity. Virtual entity does not
 * have name. Does not allow fields with same name & owner.<p>
 * 
 * Must adjust index values when new fields are added or when
 * merge happens.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Fields {
	
	int getFieldCount();

	List<Field> getFields();

	boolean fieldExists(String tableName, String fieldName);

	boolean fieldExists(String fieldName);
	
	Field getField(String fieldName);
	
	Field getField(String tableName, String fieldName);
	
	/**
	 * Same as fieldExists(tableName,fieldName).
	 */
	boolean fieldExists(FieldRef fieldRef);
	
	/**
	 * Same as getField(tableName,fieldName).
	 */
	Field getField(FieldRef fieldRef);
	
	/**
	 * Merge all fields from left & right virtual entity
	 * into a new virtual entity, adjusting indexes 
	 * in the process.
	 */
	Fields merge(Fields fields);
}
