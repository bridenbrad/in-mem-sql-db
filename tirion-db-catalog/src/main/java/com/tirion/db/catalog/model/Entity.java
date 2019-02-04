/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

import java.util.List;

import com.tirion.common.NameAware;

/**
 * Represents one logical entity. Immutable.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Entity extends NameAware {

	EntityType getType();

	int fieldCount();

	boolean hasField(String fieldName);

	Field getField(String fieldName);
	
	/**
	 * Must return them in correct order.
	 */
	List<Field> getFields();

	/**
	 * 0 based indexing.
	 */
	Field getField(int index);
	
	Entity cloneMe(String newName);
}