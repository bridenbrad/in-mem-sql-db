/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.catalog.model.function.Function;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Model {
	
	boolean isLarge(String name);
	
	boolean hasEntity(String name);
	
	Entity getEntity(String name);
	
	boolean hasField(String entityName, String fieldName);
	
	Field getField(String entityName, String fieldName);
	
	boolean hasFunction(String name);
	
	Function getFunction(String name);
}
