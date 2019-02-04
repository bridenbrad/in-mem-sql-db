/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.catalog;

import java.util.Collection;
import java.util.Set;

import com.tirion.common.Lifecycle;
import com.tirion.db.catalog.model.Entity;

/**
 * All names should be handled in case insensitive manner.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Catalog extends Lifecycle, Model {
	
	Set<String> getTableNames();
	
	void register(Entity entity);
	
	void unregister(String entityName);
	
	Collection<Entity> getTables();
}
