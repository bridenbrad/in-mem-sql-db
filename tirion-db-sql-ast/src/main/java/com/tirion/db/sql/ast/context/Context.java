/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.sql.ast.context;

import com.tirion.db.catalog.Catalog;
import com.tirion.db.catalog.Model;
import com.tirion.db.catalog.model.Entity;
import com.tirion.db.catalog.model.Field;
import com.tirion.db.catalog.model.SimpleEntity;
import com.tirion.db.sql.ast.message.MessageListener;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Context extends Model {
	
	/**
	 * Detaches this context from system wide {@link Catalog}.
	 * Initially context starts in attached mode. Should be
	 * called once we have collected all entities.
	 */
	void detach();
	
	/**
	 * Used to generate unique function aliases.
	 */
	String nextUniqueFunctionAlias();
	
	String nextUniqueEntityName();
	
	String nextUniqueEntityName(String prefix);
	
	// these 3 are used for boolean expressions
	void enterWhereScope();
	boolean isInWhereScope();
	void leaveWhereScope();
	
	void enterHavingScope();
	boolean isInHavingScope();
	void leaveHavingScope();
	
	void enterJoinScope();
	boolean isInJoinScope();
	void leaveJoinScope();
	
//	// these 2 are used for * to differentiate
//	// between function call and star-based projection
//	boolean isFunctionCallScope();
//	boolean isProjectionScope();
	
	MessageListener getListener();
	
	/**
	 * Returns table name for given field name.
	 * Throws exception in case of ambiguity.
	 */
	String findOwnerFor(String fieldName);
	
	/**
	 * Include given entity info into context.
	 */
	void register(Entity entity);
	
	/**
	 * Field index should not be used anywhere.
	 */
	void register(Field field);
	
	/**
	 * Clone this context by reusing underlying {@link MessageListener}
	 * and {@link Catalog}. Registered {@link SimpleEntity}s are not reused.
	 * New context is attached to catalog.
	 */
	Context cloneMe();
}
