/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog.model;

import java.io.Serializable;

import com.tirion.common.NameAware;
import com.tirion.common.type.Type;
import com.tirion.db.catalog.model.options.Options;

/**
 * Immutable.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Field extends NameAware, Serializable {

	boolean hasOwner();
	
	/**
	 * Name of relation that owns this field.
	 * May be null.
	 */
	String getOwner();
	
	Options getOptions();

	/**
	 * Type that was used in actual SQL create 
	 * statement. May return DATE, TIME or TIMESTAMP.
	 */
	Type getDeclaredType();

	/**
	 * For DATE it is SHORT, for TIMESTAMP it is LONG, 
	 * for TIME it is INT, for VARCHAR it is LONG.
	 */
	Type getNativeType();
	
	/**
	 * Fix string handling....
	 */
	Type getNativeTypeHack();

	/**
	 * 0-based.
	 */
	int getIndex();
	
	Field cloneMe(String newOwner, int newIndex);
	
	/**
	 * True if names & owners are equal.
	 */
	boolean areSame(Field rhs);
}